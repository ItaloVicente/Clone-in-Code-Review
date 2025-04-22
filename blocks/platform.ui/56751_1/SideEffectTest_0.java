package org.eclipse.core.databinding.observable;

import org.eclipse.core.runtime.Assert;

public final class SideEffect {
	private boolean dirty = true;
	private Runnable toRun;
	private IObservable[] dependencies;
	private boolean activated;
	private Realm realm;

	private class PrivateInterface implements IChangeListener, Runnable {
		@Override
		public void handleChange(ChangeEvent event) {
			makeDirty();
		}

		@Override
		public void run() {
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
	}

	public static SideEffect create(Runnable runnable) {
		return new SideEffect(runnable);
	}

	public static SideEffect create(Realm realm, Runnable runnable) {
		return new SideEffect(realm, runnable);
	}

	public SideEffect activate() {
		checkRealm();
		if (activated) {
			return this;
		}
		activated = true;
		update();
		return this;
	}

	private void update() {
		if (dirty && activated) {
			dirty = false;

			stopListening();

			IObservable[] newDependencies = ObservableTracker.runAndMonitor(toRun, null, null);

			if (!activated) {
				return;
			}

			for (IObservable next : newDependencies) {
				next.addChangeListener(privateInterface);
			}

			dependencies = newDependencies;
		}
	}

	public void dispose() {
		checkRealm();
		activated = false;
		stopListening();
		dependencies = null;
	}

	public SideEffect apply() {
		checkRealm();
		update();
		return this;
	}

	private void stopListening() {
		if (dependencies != null) {
			for (int i = 0; i < dependencies.length; i++) {
				IObservable observable = dependencies[i];

				observable.removeChangeListener(privateInterface);
			}
		}
	}

	protected final void makeDirty() {
		if (!dirty) {
			dirty = true;

			realm.asyncExec(privateInterface);
		}
	}

	public SideEffect invalidate() {
		checkRealm();
		makeDirty();
		return this;
	}

	private void checkRealm() {
		Assert.isTrue(realm.isCurrent(), "This operation must be run within the observable's realm"); //$NON-NLS-1$
	}
}
