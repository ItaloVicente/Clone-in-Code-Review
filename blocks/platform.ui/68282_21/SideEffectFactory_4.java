
package org.eclipse.core.internal.databinding.observable.sideeffect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.sideeffect.ICompositeSideEffect;
import org.eclipse.core.runtime.Assert;

public final class CompositeSideEffect implements ICompositeSideEffect {

	private List<ISideEffect> sideEffects;
	private int pauseCount;
	private boolean isDisposed;
	private Realm realm;

	private List<Consumer<ISideEffect>> disposeListeners;

	public CompositeSideEffect() {
		realm = Realm.getDefault();
		sideEffects = new ArrayList<>();
	}

	private void checkRealm() {
		Assert.isTrue(realm.isCurrent(), "This operation must be run within the observable's realm"); //$NON-NLS-1$
	}

	@Override
	public void dispose() {
		checkRealm();
		if (isDisposed) {
			return;
		}
		sideEffects.forEach(s -> s.dispose());
		sideEffects.clear();
		isDisposed = true;
		if (disposeListeners != null) {
			List<Consumer<ISideEffect>> listeners = disposeListeners;
			disposeListeners = null;
			listeners.forEach(dc -> dc.accept(CompositeSideEffect.this));
		}
	}

	@Override
	public boolean isDisposed() {
		checkRealm();
		return this.isDisposed;
	}

	@Override
	public void addDisposeListener(Consumer<ISideEffect> disposalConsumer) {
		checkRealm();
		if (isDisposed()) {
			return;
		}
		if (this.disposeListeners == null) {
			this.disposeListeners = new ArrayList<>();
		}
		this.disposeListeners.add(disposalConsumer);
	}

	@Override
	public void removeDisposeListener(Consumer<ISideEffect> disposalConsumer) {
		checkRealm();
		if (this.disposeListeners == null) {
			return;
		}
		this.disposeListeners.remove(disposalConsumer);
	}

	@Override
	public void pause() {
		checkRealm();
		pauseCount++;
		if (pauseCount == 1) {
			sideEffects.forEach(s -> s.pause());
		}
	}

	@Override
	public void resume() {
		checkRealm();
		pauseCount--;
		if (pauseCount == 0) {
			sideEffects.forEach(s -> s.resume());
		}
	}

	@Override
	public void resumeAndRunIfDirty() {
		checkRealm();
		pauseCount--;
		if (pauseCount == 0) {
			sideEffects.forEach(s -> s.resumeAndRunIfDirty());
		}
	}

	@Override
	public void runIfDirty() {
		checkRealm();
		if (pauseCount <= 0) {
			sideEffects.forEach(s -> s.runIfDirty());
		}
	}

	@Override
	public void add(ISideEffect sideEffect) {
		checkRealm();
		if (!sideEffect.isDisposed()) {
			sideEffects.add(sideEffect);
			if (pauseCount > 0) {
				sideEffect.pause();
			}
			sideEffect.addDisposeListener(this::remove);
		}
	}

	@Override
	public void remove(ISideEffect sideEffect) {
		checkRealm();
		sideEffects.remove(sideEffect);
		if (!sideEffect.isDisposed()) {
			if (pauseCount > 0) {
				sideEffect.resume();
			}
			sideEffect.removeDisposeListener(this::remove);
		}
	}
}
