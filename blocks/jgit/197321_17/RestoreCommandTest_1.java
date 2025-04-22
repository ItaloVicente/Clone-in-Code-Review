
package org.eclipse.jgit.pgm;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RestoreCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StopOptionHandler;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Command(usage = "usage_RestoreWorkingTreeFile"
class Restore extends TextBuiltin {

	@Argument(metaVar = "metaVar_path"
	@Option(name = "--"
	private List<String> filepatterns = new ArrayList<>();

	@Option(name = "--cached"
	private boolean cached;

	@Override
	protected void run() {
		if(!addAll) {
			for (String name : filepatterns) {
				File p = new File(db.getWorkTree()
				if (!p.exists()) {
					throw die(MessageFormat
							.format(CLIText.get().pathspecDidNotMatch
				}
			}
		}

		try (Git git = new Git(db)) {
			RestoreCommand command = git.restore();
			command.setCached(cached);
			for (String p : filepatterns) {
				command.addFilepattern(p);
			}
			command.call();
		} catch (GitAPIException | NoWorkTreeException e) {
			throw die(e.getMessage()
		}
	}
}
