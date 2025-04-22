
package org.eclipse.core.databinding.observable.sideeffect;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.core.databinding.observable.ISideEffect;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.internal.databinding.observable.sideeffect.SideEffectFactory;

public interface ISideEffectFactory {

	static ISideEffectFactory createFactory() {
		return new SideEffectFactory();
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
