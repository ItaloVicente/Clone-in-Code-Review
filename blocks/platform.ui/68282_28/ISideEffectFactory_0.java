
package org.eclipse.core.databinding.observable.sideeffect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.runtime.Assert;

public final class CompositeSideEffect implements ISideEffect {

	private final List<ISideEffect> sideEffects;
	private int pauseDepth;
	private boolean isDisposed;
	private final Realm realm;

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
		pauseDepth++;
		if (pauseDepth == 1) {
			sideEffects.forEach(s -> s.pause());
		}
	}

	@Override
	public void resume() {
		checkRealm();
		pauseDepth--;
		if (pauseDepth < 0) {
			throw new IllegalStateException(
					"The resume() method was called more times than pause()."); //$NON-NLS-1$
		} else if (pauseDepth == 0) {
			sideEffects.forEach(s -> s.resume());
		}
	}

	@Override
	public void resumeAndRunIfDirty() {
		checkRealm();
		pauseDepth--;
		if (pauseDepth == 0) {
			sideEffects.forEach(s -> s.resumeAndRunIfDirty());
		}
	}

	@Override
	public void runIfDirty() {
		checkRealm();
		if (pauseDepth <= 0) {
			sideEffects.forEach(s -> s.runIfDirty());
		}
	}

	public void add(ISideEffect sideEffect) {
		checkRealm();
		if (!sideEffect.isDisposed()) {
			sideEffects.add(sideEffect);
			if (pauseDepth > 0) {
				sideEffect.pause();
			}
			sideEffect.addDisposeListener(this::remove);
		}
	}

	public void remove(ISideEffect sideEffect) {
		checkRealm();
		sideEffects.remove(sideEffect);
		if (!sideEffect.isDisposed()) {
			if (pauseDepth > 0) {
				sideEffect.resume();
			}
			sideEffect.removeDisposeListener(this::remove);
		}
	}
}
