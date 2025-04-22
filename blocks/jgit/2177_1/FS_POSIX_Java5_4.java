package org.eclipse.jgit.util;

import java.io.File;
import java.io.IOException;

abstract class FS_POSIX extends FS {

	public File gitPrefix() throws IOException {
		String gitExe = scanPath(SystemReader.getInstance().getenv("PATH")
				"git");
		if (gitExe != null)
			return new File(gitExe).getAbsoluteFile().getParentFile()
					.getParentFile();
		return super.gitPrefix();
	}
}
