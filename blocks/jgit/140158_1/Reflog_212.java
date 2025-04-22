
package org.eclipse.jgit.pgm;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.util.FS;
import org.kohsuke.args4j.Argument;

@Command(common = false
class ReceivePack extends TextBuiltin {
	@Argument(index = 0
	File dstGitdir;

	@Override
	protected final boolean requiresRepository() {
		return false;
	}

	@Override
	protected void run() {
		final org.eclipse.jgit.transport.ReceivePack rp;

		try {
			FileKey key = FileKey.lenient(dstGitdir
		} catch (RepositoryNotFoundException notFound) {
			throw die(MessageFormat.format(CLIText.get().notAGitRepository
					dstGitdir.getPath()));
		} catch (IOException e) {
			throw die(e.getMessage()
		}

		rp = new org.eclipse.jgit.transport.ReceivePack(db);
		try {
			rp.receive(ins
		} catch (IOException e) {
			throw die(e.getMessage()
		}
	}
}
