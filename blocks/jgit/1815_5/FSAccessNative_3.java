package org.eclipse.jgit.util.fs;

import java.io.File;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.util.FS;

public class FSAccessJava extends FSAccess {

	FSAccessJava() {
	}

	@Override
	public LStat lstat(FS fs
			NotDirectoryException {
		FileMode mode;
		if (file.isDirectory()) {
			if (new File(file
				mode = FileMode.GITLINK;
			else
				mode = FileMode.TREE;
		} else if (fs.canExecute(file))
			mode = FileMode.EXECUTABLE_FILE;
		else
			mode = FileMode.REGULAR_FILE;

		long sz = file.length();
		if (sz < 0)
			throw new NoSuchFileException(file.getAbsolutePath());

		return new LStat(file.lastModified()
	}

	@Override
	public boolean isNativeImplementation() {
		return false;
	}

}
