package org.eclipse.jgit.util.fs;

import java.io.File;

public abstract class FSAccess {

	private static boolean isNativeImplementation;

	public final static FSAccess INSTANCE;

	static {
		FSAccess fsa = null;
		try {
			System.loadLibrary("jgitnative");
			fsa = new FSAccessNative();
			isNativeImplementation = true;
			System.out.println("successfully loaded native implementation");
		} catch (IllegalArgumentException e) {
			isNativeImplementation = false;
		} catch (SecurityException e) {
			isNativeImplementation = false;
		}
		if (fsa == null) {
			fsa = new FSAccessJava();
			System.out
					.println("failed loading native implementation
		}
		INSTANCE = fsa;
	}

	public static final boolean isNativeImplementation() {
		return isNativeImplementation;
	}

	public abstract LStat lstat(File file) throws NoSuchFileException
			NotDirectoryException;
}
