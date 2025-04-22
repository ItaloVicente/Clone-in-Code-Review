
package org.eclipse.jgit.util.fs;

import java.io.File;

public abstract class FileAccess {
	public abstract FileInfo lstat(File file)
			throws AccessDeniedException
			NotDirectoryException;
}
