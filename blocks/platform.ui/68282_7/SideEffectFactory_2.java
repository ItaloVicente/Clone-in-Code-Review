
package org.eclipse.core.internal.databinding.observable.sideeffect;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.core.databinding.observable.sideeffect.ICompositeSideEffect;

public final class CompositeSideEffect implements ICompositeSideEffect {

	private List<ISideEffect> sideEffects;

	public CompositeSideEffect() {
		sideEffects = new ArrayList<>();
	}

	@Override
	public void dispose() {
		sideEffects.forEach(s -> s.dispose());
		sideEffects.clear();
	}

	@Override
	public void pause() {
		sideEffects.forEach(s -> s.pause());
	}

	@Override
	public void resume() {
		sideEffects.forEach(s -> s.resume());
	}

	@Override
	public void resumeAndRunIfDirty() {
		sideEffects.forEach(s -> s.resumeAndRunIfDirty());
	}

	@Override
	public void runIfDirty() {
		sideEffects.forEach(s -> s.runIfDirty());
	}

	@Override
	public void add(ISideEffect sideEffect) {
		sideEffects.add(sideEffect);
	}

	@Override
	public void remove(ISideEffect sideEffect) {
		sideEffects.remove(sideEffect);
	}
}
