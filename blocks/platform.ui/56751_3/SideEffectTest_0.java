package org.eclipse.core.databinding.observable;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.core.runtime.Assert;

public final class SideEffect implements IDisposable {
	private static final IDisposable NULL_DISPOSABLE = () -> {
	};
	private boolean dirty;
	private boolean asyncScheduled;
	private boolean resumed;
	private Runnable toRun;
	private IObservable[] dependencies;
	private Realm realm;

	private class PrivateInterface implements IChangeListener, Runnable {
		@Override
		public void handleChange(ChangeEvent event) {
			markDirtyInternal();
		}

		@Override
		public void run() {
			asyncScheduled = false;
			update();
		}
	}

	private PrivateInterface privateInterface = new PrivateInterface();

	private SideEffect(Runnable runnable) {
		this(Realm.getDefault(), runnable);
	}

	private SideEffect(Realm realm, Runnable runnable) {
		toRun = runnable;
		this.realm = realm;
		this.dirty = true;
	}

	private SideEffect(IObservable[] dependencies, Runnable runnable) {
		this.dependencies = dependencies;
		this.toRun = runnable;
		this.dirty = false;
		this.resumed = true;
		this.realm = Realm.getDefault();

		for (IObservable next : dependencies) {
			next.addChangeListener(privateInterface);
		}
	}

	public static SideEffect createPaused(Runnable runnable) {
		return new SideEffect(runnable);
	}

	public static SideEffect createPaused(Realm realm, Runnable runnable) {
		return new SideEffect(realm, runnable);
	}

	public static IDisposable create(Runnable runnable) {
		IObservable[] dependencies = ObservableTracker.runAndMonitor(runnable, null, null);

		if (dependencies.length == 0) {
			return NULL_DISPOSABLE;
		}

		return new SideEffect(dependencies, runnable);
	}

	public static <T> IDisposable create(Supplier<T> supplier, Consumer<T> consumer) {
		return create(() -> {
			T value = supplier.get();

			ObservableTracker.setIgnore(true);
			try {
				consumer.accept(value);
			} finally {
				ObservableTracker.setIgnore(false);
			}
		});
	}

	public void resume() {
		checkRealm();
		if (resumed) {
			return;
		}
		resumed = true;
		if (dirty) {
			scheduleUpdate();
		}
	}

	public void pause() {
		checkRealm();
		resumed = false;
		if (dirty) {
			stopListening();
			dependencies = null;
		}
	}

	public void resumeAndRunIfDirty() {
		checkRealm();
		resumed = true;
		update();
	}

	private void update() {
		if (dirty && resumed) {
			dirty = false;

			stopListening();

			IObservable[] newDependencies = ObservableTracker.runAndMonitor(toRun, null, null);

			if (!resumed) {
				return;
			}

			for (IObservable next : newDependencies) {
				next.addChangeListener(privateInterface);
			}

			dependencies = newDependencies;
		}
	}

	@Override
	public void dispose() {
		checkRealm();
		resumed = false;
		stopListening();
		dependencies = null;
	}

	public void runIfDirty() {
		checkRealm();
		update();
	}

	private void stopListening() {
		if (dependencies != null) {
			for (int i = 0; i < dependencies.length; i++) {
				IObservable observable = dependencies[i];

				observable.removeChangeListener(privateInterface);
			}
		}
	}

	private void markDirtyInternal() {
		if (!dirty) {
			dirty = true;

			if (resumed) {
				scheduleUpdate();
			} else {
				stopListening();
				dependencies = null;
			}
		}
	}

	private void scheduleUpdate() {
		if (this.asyncScheduled) {
			return;
		}

		this.asyncScheduled = true;
		realm.asyncExec(privateInterface);
	}

	public void markDirty() {
		checkRealm();
		markDirtyInternal();
	}

	private void checkRealm() {
		Assert.isTrue(realm.isCurrent(), "This operation must be run within the observable's realm"); //$NON-NLS-1$
	}
}
