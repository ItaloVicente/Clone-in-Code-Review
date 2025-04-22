
package org.eclipse.jgit.util.fs;

import java.io.File;

import org.eclipse.jgit.util.NativeLibrary;

public class FileAccessNative extends FileAccess {
	public FileAccessNative() {
		NativeLibrary.assertLoaded();
	}

	public FileInfo lstat(File file) throws NoSuchFileException
			NotDirectoryException {
		return lstatImp(file.getPath());
	}

	private static native FileInfo lstatImp(String path);
}
