
package org.eclipse.jgit.util;

import java.text.MessageFormat;

import org.eclipse.jgit.JGitText;

public final class NativeLibrary {
	private static final String SYSTEM_PROPERTY = "jgit.native.skip";

	private static final String NAME = "jgit_native";

	private static final Throwable state;

	static {
		Throwable myState;
		try {
			if (Boolean.getBoolean(SYSTEM_PROPERTY)) {
				myState = new Disabled();
			} else {
				System.loadLibrary(NAME);
				myState = new Loaded();
			}
		} catch (UnsatisfiedLinkError linkError) {
			try {
				final String propName = "java.library.path";
				final String path = System.getProperty(propName);
				myState = new UnsatisfiedLinkError(MessageFormat.format(
						JGitText.get().noNativeLibraryIn
				myState.initCause(linkError);
			} catch (SecurityException noPath) {
				myState = linkError;
			}
		} catch (Throwable cannotLink) {
			myState = cannotLink;
		}
		state = myState;
	}

	public static boolean isLoaded() {
		return state instanceof Loaded;
	}

	public static boolean isDisabled() {
		return state instanceof Disabled;
	}

	public static Throwable getFailure() {
		if (state instanceof Loaded)
			return null;
		else
			return state;
	}

	public static void assertLoaded() throws UnsatisfiedLinkError {
		if (!isLoaded()) {
			UnsatisfiedLinkError e = new UnsatisfiedLinkError(NAME);
			e.initCause(getFailure());
			throw e;
		}
	}

	private NativeLibrary() {
	}

	private static class Loaded extends Exception {
		private static final long serialVersionUID = 1L;
	}

	private static class Disabled extends Exception {
		private static final long serialVersionUID = 1L;

		Disabled() {
			super(SYSTEM_PROPERTY + "=true");
		}
	}
}
