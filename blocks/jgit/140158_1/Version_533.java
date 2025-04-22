
package org.eclipse.jgit.pgm;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.util.FS;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = false
class UploadPack extends TextBuiltin {
	@Option(name = "--timeout"
	int timeout = -1;

	@Argument(index = 0
	File srcGitdir;

	@Override
	protected final boolean requiresRepository() {
		return false;
	}

	@Override
	protected void run() {
		try {
			FileKey key = FileKey.lenient(srcGitdir
			org.eclipse.jgit.transport.UploadPack up = new org.eclipse.jgit.transport.UploadPack(
					db);
			if (0 <= timeout) {
				up.setTimeout(timeout);
			}
			up.upload(ins
		} catch (RepositoryNotFoundException notFound) {
			throw die(MessageFormat.format(CLIText.get().notAGitRepository
					srcGitdir.getPath()));
		} catch (IOException e) {
			throw die(e.getMessage()
		}
	}
}
