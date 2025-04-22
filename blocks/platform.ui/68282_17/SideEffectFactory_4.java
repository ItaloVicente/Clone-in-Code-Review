
package org.eclipse.core.internal.databinding.observable.sideeffect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.core.databinding.observable.sideeffect.ICompositeSideEffect;
import org.eclipse.core.runtime.ListenerList;

public final class CompositeSideEffect implements ICompositeSideEffect {

	private List<ISideEffect> sideEffects;
	private int pauseCount;
	private boolean isDisposed;

	private ListenerList<Consumer<ISideEffect>> disposalConsumer;

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
		if (disposalConsumer != null) {
			disposalConsumer.forEach(dc -> dc.accept(CompositeSideEffect.this));
			disposalConsumer.clear();
			disposalConsumer = null;
		}
	}

	@Override
	public boolean isDisposed() {
		return this.isDisposed;
	}

	@Override
	public void addDisposeListener(Consumer<ISideEffect> disposalConsumer) {
		if (null == this.disposalConsumer) {
			this.disposalConsumer = new ListenerList<>();
		}
		this.disposalConsumer.add(disposalConsumer);
	}

	@Override
	public void removeDisposeListener(Consumer<ISideEffect> disposalConsumer) {
		this.disposalConsumer.remove(disposalConsumer);
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
}
