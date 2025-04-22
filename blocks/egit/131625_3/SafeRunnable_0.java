package org.eclipse.egit.core.internal;

import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;

public final class SafeRunnable {

	private SafeRunnable() {
	}

	public static void run(Runner code) {
		SafeRunner.run(code);
	}

	@FunctionalInterface
	public interface Runner extends ISafeRunnable {

		@Override
		default void handleException(Throwable exception) {
		}
	}
}
