package org.eclipse.core.internal.databinding.observable;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.core.databinding.observable.ObservableTracker;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.runtime.Assert;

public final class SideEffect implements ISideEffect {
	public static final ISideEffect NULL_SIDE_EFFECT = new ISideEffect() {
		@Override
		public void dispose() {
		}

		@Override
		public void pause() {
		}

		@Override
		public void resume() {
		}

		@Override
		public void resumeAndRunIfDirty() {
		}

		@Override
		public void runIfDirty() {
		}
	};

	private boolean dirty;
	private boolean asyncScheduled;
	private boolean resumed;
	private Runnable runnable;
	private IObservable[] dependencies;
	private Realm realm;

	private PrivateInterface privateInterface = new PrivateInterface();

	public SideEffect(Runnable runnable) {
		this(Realm.getDefault(), runnable);
	}

	public SideEffect(Realm realm, Runnable runnable) {
		this.runnable = runnable;
		this.realm = realm;
		this.dirty = true;
	}

	public SideEffect(Runnable runnable, IObservable... dependencies) {
		this.dependencies = dependencies;
		this.runnable = runnable;
		this.dirty = false;
		this.resumed = true;
		this.realm = Realm.getDefault();

		for (IObservable next : dependencies) {
			next.addChangeListener(privateInterface);
		}
	}

	@Override
	public void resume() {
		checkState();
		if (resumed) {
			return;
		}
		resumed = true;
		if (dirty) {
			scheduleUpdate();
		}
	}


	@Override
	public void pause() {
		checkState();
		resumed = false;
		if (dirty) {
			stopListening();
			dependencies = null;
		}
	}

	@Override
	public void resumeAndRunIfDirty() {
		checkState();
		resumed = true;
		update();
	}

	private void update() {
		if (dirty && resumed) {
			dirty = false;

			stopListening();

			IObservable[] newDependencies = ObservableTracker.runAndMonitor(runnable, null, null);

			if (isDisposed()) {
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
		runnable = null;
	}

	private boolean isDisposed() {
		return runnable == null;
	}

	@Override
	public void runIfDirty() {
		checkState();
		update();
	}

	private void stopListening() {
		if (dependencies != null) {
			for (IObservable observable : dependencies) {
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

	private void checkState() {
		if (isDisposed()) {
			throw new IllegalStateException("This SideEffect has been disposed!"); //$NON-NLS-1$
		}
		checkRealm();
	}

	private void checkRealm() {
		Assert.isTrue(realm.isCurrent(), "This operation must be run within the observable's realm"); //$NON-NLS-1$
	}

	public static <T> Runnable makeRunnable(Supplier<T> supplier, Consumer<T> consumer) {
		return () -> {
			T value = supplier.get();

			ObservableTracker.setIgnore(true);
			try {
				consumer.accept(value);
			} finally {
				ObservableTracker.setIgnore(false);
			}
		};
	}

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
}
