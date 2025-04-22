package org.eclipse.jgit.lib;

public class MoreAsserts {
	public static <T extends Throwable> T assertThrows(Class<T> expected
			ThrowingRunnable r) {
		try {
			r.run();
		} catch (Throwable actual) {
			if (expected.isAssignableFrom(actual.getClass())) {
				@SuppressWarnings("unchecked")
				T toReturn = (T) actual;
				return toReturn;
			}
			throw new AssertionError("Expected " + expected.getSimpleName()
					+ "
		}
		throw new AssertionError(
				"Expected " + expected.getSimpleName() + " to be thrown");
	}

	public interface ThrowingRunnable {
		void run() throws Throwable;
	}

	private MoreAsserts() {
	}
}
