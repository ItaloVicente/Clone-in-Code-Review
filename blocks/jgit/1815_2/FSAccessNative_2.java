package org.eclipse.jgit.util.fs;

import java.io.File;

public class FSAccessJava extends FSAccess {

	FSAccessJava() {
	}

	@Override
	public LStat lstat(File file) throws NoSuchFileException
			NotDirectoryException {
		return new LStat(file.lastModified()
	}

}
