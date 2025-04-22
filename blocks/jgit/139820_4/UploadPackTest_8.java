	private static <T extends Throwable> T assertThrows(Class<T> expected
			ThrowingRunnable r) {
		try {
			r.run();
			throw new AssertionError("Expected " + expected.getSimpleName() +
					" to be thrown");
		} catch (Throwable actual) {
			if (expected.isAssignableFrom(actual.getClass())) {
				return (T) actual;
			}
			throw new AssertionError("Expected " + expected.getSimpleName() +
					"
		}
	}

	private interface ThrowingRunnable {
		void run() throws Throwable;
	}

