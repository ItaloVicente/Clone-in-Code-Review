package org.eclipse.jgit.util.fs;

import java.io.File;

import org.eclipse.jgit.util.FS;

public abstract class FSAccess {

	private static Throwable nativeLoadError;

	public final static FSAccess INSTANCE;

	static {
		FSAccess fsa = null;
		try {
			fsa = new FSAccessNative();
		} catch (IllegalArgumentException e) {
			nativeLoadError = e;
		} catch (SecurityException e) {
			nativeLoadError = e;
		}
		if (fsa == null) {
			fsa = new FSAccessJava();
		}
		INSTANCE = fsa;
	}

	public static final Throwable getNativeImplementationLoadException() {
		return nativeLoadError;
	}

	public abstract boolean isNativeImplementation();

	public abstract LStat lstat(FS fs
			NoSuchFileException
}
