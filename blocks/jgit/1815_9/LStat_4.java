package org.eclipse.jgit.util.fs;

import java.io.File;

import org.eclipse.jgit.util.FS;

public class FSAccessNative extends FSAccess {

	static {
		System.loadLibrary("jgit_native");
	}

	FSAccessNative() {
	}

	private static final native LStat lstatImpl(String path);

	public LStat lstat(FS fs
			NotDirectoryException {
		return lstatImpl(file.getPath());
	}

	@Override
	public boolean isNativeImplementation() {
		return true;
	}
}
