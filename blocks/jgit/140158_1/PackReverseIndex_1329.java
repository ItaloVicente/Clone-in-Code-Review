
package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;

public class PackLock {
	private final File keepFile;

	public PackLock(File packFile
		final File p = packFile.getParentFile();
		final String n = packFile.getName();
		keepFile = new File(p
	}

	public boolean lock(String msg) throws IOException {
		if (msg == null)
			return false;
		final LockFile lf = new LockFile(keepFile);
		if (!lf.lock())
			return false;
		lf.write(Constants.encode(msg));
		return lf.commit();
	}

	public void unlock() throws IOException {
		FileUtils.delete(keepFile);
	}
}
