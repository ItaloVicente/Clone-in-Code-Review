package org.eclipse.jgit.util.fs;

import java.io.File;

public class FSAccessNative extends FSAccess {

	static {
	}

	public FSAccessNative() {
	}

	private static final native int[] lstatImpl(String path);

	public LStat lstat(File file) throws NoSuchFileException
			NotDirectoryException {
		String path = file.getAbsolutePath();
		int[] rawlstat = lstatImpl(path);
		if (rawlstat.length != 10)
			throw new IllegalArgumentException("lstat() didn't return int[10]");

		return new LStat(rawlstat);
	}
}
