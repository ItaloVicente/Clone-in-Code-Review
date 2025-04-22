
package org.eclipse.core.internal.databinding.observable.sideeffect;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.core.databinding.observable.sideeffect.ICompositeSideEffect;

public final class CompositeSideEffect implements ICompositeSideEffect {

	private List<ISideEffect> sideEffects;
	private int pauseCount;
	private boolean isDisposed;

	public CompositeSideEffect() {
		sideEffects = new ArrayList<>();
	}

	@Override
	public void dispose() {
		if (isDisposed) {
			return;
		}
		sideEffects.forEach(s -> s.dispose());
		sideEffects.clear();
		isDisposed = true;
	}

	@Override
	public void pause() {
		pauseCount++;
		if (pauseCount == 1) {
			sideEffects.forEach(s -> s.pause());
		}
	}

	@Override
	public void resume() {
		pauseCount--;
		if (pauseCount == 0) {
			sideEffects.forEach(s -> s.resume());
		}
	}

	@Override
	public void resumeAndRunIfDirty() {
		pauseCount--;
		if (pauseCount == 0) {
			sideEffects.forEach(s -> s.resumeAndRunIfDirty());
		}
	}

	@Override
	public void runIfDirty() {
		if (pauseCount <= 0) {
			sideEffects.forEach(s -> s.runIfDirty());
		}
	}

	@Override
	public void add(ISideEffect sideEffect) {
		if (!sideEffect.isDisposed()) {
			sideEffects.add(sideEffect);
			if (pauseCount > 0) {
				sideEffect.pause();
			}
		}
	}

	@Override
	public void remove(ISideEffect sideEffect) {
		sideEffects.remove(sideEffect);
		if (pauseCount > 0) {
			sideEffect.resume();
		}
	}

	@Override
	public boolean isDisposed() {
		return this.isDisposed;
	}
}
