
package org.eclipse.jgit.pgm.debug;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.pgm.Command;
import org.eclipse.jgit.pgm.TextBuiltin;
import org.eclipse.jgit.pgm.internal.CLIText;

@Command(usage = "usage_WriteDirCache")
class WriteDirCache extends TextBuiltin {
	@Override
	protected void run() throws Exception {
		final DirCache cache = db.readDirCache();
		if (!cache.lock())
			throw die(CLIText.get().failedToLockIndex);
		cache.read();
		cache.write();
		if (!cache.commit())
			throw die(CLIText.get().failedToCommitIndex);
	}
}
