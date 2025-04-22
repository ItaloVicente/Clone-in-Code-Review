package org.eclipse.jgit.util.fs.os;

import org.eclipse.jgit.util.fs.LStat;

class NativeFSAccess {

	static {
		System.out.println("java.library.path "
				+ System.getProperty("java.library.path"));
		System.loadLibrary("jgitnative");
	}

	private NativeFSAccess() {
	}

	private static final native int[] lstatImpl(String path);

	public static final LStat lstat(String path) {
		int[] rawlstat = lstatImpl(path);
		if (rawlstat.length != 10)
			throw new IllegalArgumentException("lstat() didn't return int[10]");

		return new LStat(rawlstat);
	}
}
