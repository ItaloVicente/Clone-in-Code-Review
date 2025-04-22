
package org.eclipse.core.internal.databinding.observable;

import java.util.function.Consumer;

import org.eclipse.core.databinding.observable.ICompositeSideEffect;
import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;

public final class CompositeSideEffect implements ICompositeSideEffect {

	private IObservableList<ISideEffect> sideEffects;

	public CompositeSideEffect() {
		this(Realm.getDefault());
	}

	public CompositeSideEffect(Realm realm) {
		sideEffects = new WritableList<>(realm);
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
	public Consumer<ISideEffect> getSideEffectConsumer() {
		return sideEffects::add;
	}

}
