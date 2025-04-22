
package org.eclipse.jgit.util.fs;

import java.io.File;

import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.util.FS;

public class FileAccessJava extends FileAccess {
	private final FS fs;

	public FileAccessJava(FS fs) {
		this.fs = fs;
	}

	@Override
	public FileInfo lstat(File file) throws NoSuchFileException
			NotDirectoryException {

		final long mtime = file.lastModified();
		if (mtime == 0 && !file.exists())
			throw new NoSuchFileException(file.getPath());

		final int mode;
		final long sz;
		if (file.isDirectory()) {
			mode = FileMode.TREE.getBits();
			sz = 0;

		} else if (file.isFile()) {
			if (fs.canExecute(file))
				mode = FileMode.EXECUTABLE_FILE.getBits();
			else
				mode = FileMode.REGULAR_FILE.getBits();
			sz = file.length();

		} else {
			mode = 0;
			sz = 0;
		}

		return new FileInfo(mtime
	}
}
