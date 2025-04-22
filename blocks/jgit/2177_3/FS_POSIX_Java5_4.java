package org.eclipse.jgit.util;

import java.io.File;
import java.nio.charset.Charset;
import java.security.AccessController;
import java.security.PrivilegedAction;

abstract class FS_POSIX extends FS {
	@Override
	public File gitPrefix() {
		String path = SystemReader.getInstance().getenv("PATH");
		File gitExe = searchPath(path
		if (gitExe != null)
			return gitExe.getParentFile().getParentFile();

		if (isMacOS()) {
			String w = readPipe(userHome()
					new String[] { "bash"
					Charset.defaultCharset().name());
			return new File(w).getParentFile().getParentFile();
		}

		return null;
	}

	private static boolean isMacOS() {
		final String osDotName = AccessController
				.doPrivileged(new PrivilegedAction<String>() {
					public String run() {
						return System.getProperty("os.name");
					}
				});
		return "Mac OS X".equals(osDotName);
	}
}
