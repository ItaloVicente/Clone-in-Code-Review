
package org.eclipse.core.databinding.observable;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.core.databinding.observable.value.ComputedValue;
import org.eclipse.core.databinding.observable.value.WritableValue;

public interface ISideEffect {
	void dispose();

	void pause();

	void resume();

	void resumeAndRunIfDirty();

	void runIfDirty();

	static ISideEffect createPaused(Runnable runnable) {
		return new SideEffect(runnable);
	}

	static ISideEffect createPaused(Realm realm, Runnable runnable) {
		return new SideEffect(realm, runnable);
	}

	static ISideEffect create(Runnable runnable) {
		IObservable[] dependencies = ObservableTracker.runAndMonitor(runnable, null, null);

		if (dependencies.length == 0) {
			return SideEffect.NULL_SIDE_EFFECT;
		}

		return new SideEffect(runnable, dependencies);
	}

	static <T> ISideEffect create(Supplier<T> supplier, Consumer<T> consumer) {
		return ISideEffect.create(() -> {
			T value = supplier.get();

			ObservableTracker.setIgnore(true);
			try {
				consumer.accept(value);
			} finally {
				ObservableTracker.setIgnore(false);
			}
		});
	}

	static <T> ISideEffect runOnce(Supplier<T> supplier, Consumer<T> consumer) {
		final ISideEffect[] result = new ISideEffect[1];

		Runnable theRunnable = () -> {
			T value = supplier.get();

			if (value != null) {
				ObservableTracker.setIgnore(true);
				try {
					consumer.accept(value);
				} finally {
					ObservableTracker.setIgnore(false);
				}

				result[0].dispose();
			}
		};

		result[0] = ISideEffect.createPaused(theRunnable);
		result[0].resume();

		return result[0];
	}
}
