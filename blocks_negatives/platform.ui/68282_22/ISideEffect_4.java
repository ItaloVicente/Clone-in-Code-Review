	static <T> ISideEffect consumeOnceAsync(Supplier<T> supplier, Consumer<T> consumer) {
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
