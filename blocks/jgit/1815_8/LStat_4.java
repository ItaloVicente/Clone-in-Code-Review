package org.eclipse.jgit.util.fs;

import java.io.File;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.util.FS;

public class FSAccessNative extends FSAccess {

	static {
		System.loadLibrary("jgitnative");
	}

	FSAccessNative() {
	}

	private static final native int[] lstatImpl(String path);

	public LStat lstat(FS fs
			NotDirectoryException {
		String path = file.getPath();
		int[] rawlstat = lstatImpl(path);
		if (rawlstat.length != 11)
			throw new IllegalArgumentException(
					JGitText.get().lstatImplIllegalResult);

		return new LStat(rawlstat);
	}

	@Override
	public boolean isNativeImplementation() {
		return true;
	}
}
