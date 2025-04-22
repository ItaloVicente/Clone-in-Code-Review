package org.eclipse.jgit.util;

import java.io.File;

abstract class FS_POSIX extends FS {

	@Override
	public File gitPrefix() {
		File gitExe = searchPath(SystemReader.getInstance().getenv("PATH")
		if (gitExe != null)
			return gitExe.getParentFile().getParentFile();
		return super.gitPrefix();
	}
}
