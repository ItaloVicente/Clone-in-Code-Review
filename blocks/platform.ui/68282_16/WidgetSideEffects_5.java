
package org.eclipse.core.internal.databinding.observable.sideeffect;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.sideeffect.ISideEffectFactory;

public final class SideEffectFactory implements ISideEffectFactory {

	private Consumer<ISideEffect> sideEffectConsumer;
	private Consumer<ISideEffect> disposalConsumer;

	public SideEffectFactory(Consumer<ISideEffect> sideEffectConsumer) {
		this.sideEffectConsumer = sideEffectConsumer;
	}

	public SideEffectFactory(Consumer<ISideEffect> sideEffectConsumer, Consumer<ISideEffect> disposalConsumer) {
		this.sideEffectConsumer = sideEffectConsumer;
		this.disposalConsumer = disposalConsumer;
	}

	@Override
	public ISideEffect createPaused(Runnable runnable) {
		ISideEffect sideEffect = ISideEffect.createPaused(runnable);
		sideEffect.addDisposeListener(disposalConsumer);
		sideEffectConsumer.accept(sideEffect);

		return sideEffect;
	}

	@Override
	public ISideEffect createPaused(Realm realm, Runnable runnable) {
		ISideEffect sideEffect = ISideEffect.createPaused(realm, runnable);
		sideEffect.addDisposeListener(disposalConsumer);
		sideEffectConsumer.accept(sideEffect);

		return sideEffect;
	}

	@Override
	public ISideEffect create(Runnable runnable) {
		ISideEffect sideEffect = ISideEffect.create(runnable);
		sideEffect.addDisposeListener(disposalConsumer);
		sideEffectConsumer.accept(sideEffect);

		return sideEffect;
	}

	@Override
	public <T> ISideEffect create(Supplier<T> supplier, Consumer<T> consumer) {
		ISideEffect sideEffect = ISideEffect.create(supplier, consumer);
		sideEffect.addDisposeListener(disposalConsumer);
		sideEffectConsumer.accept(sideEffect);

		return sideEffect;
	}

	@Override
	public <T> ISideEffect createResumed(Supplier<T> supplier, Consumer<T> consumer) {
		ISideEffect sideEffect = ISideEffect.createResumed(supplier, consumer);
		sideEffect.addDisposeListener(disposalConsumer);
		sideEffectConsumer.accept(sideEffect);

		return sideEffect;
	}
}
