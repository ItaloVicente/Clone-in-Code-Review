
package org.eclipse.core.databinding.observable;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.core.internal.databinding.observable.SideEffectFactory;

public interface ISideEffectFactory {

	static ISideEffectFactory createFactory() {
		ICompositeSideEffect compositeSideEffect = ICompositeSideEffect.create();

		return new SideEffectFactory(compositeSideEffect::add);
	}

	static ISideEffectFactory createFactory(Consumer<ISideEffect> sideEffectConsumer) {
		return new SideEffectFactory(sideEffectConsumer);
	}

	ISideEffect createPaused(Runnable runnable);

	ISideEffect createPaused(Realm realm, Runnable runnable);

	ISideEffect create(Runnable runnable);

	<T> ISideEffect create(Supplier<T> supplier, Consumer<T> consumer);

	<T> ISideEffect createResumed(Supplier<T> supplier, Consumer<T> consumer);

	<T> ISideEffect consumeOnceAsync(Supplier<T> supplier, Consumer<T> consumer);

}
