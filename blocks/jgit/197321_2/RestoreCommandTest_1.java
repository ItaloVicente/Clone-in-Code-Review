
package org.eclipse.jgit.pgm;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RestoreCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StopOptionHandler;

import java.util.ArrayList;
import java.util.List;

@Command(usage = "usage_RestoreWorkingTreeFile"
class Restore extends TextBuiltin {
	@Argument(metaVar = "metaVar_path"
	@Option(name = "--"
	private List<String> paths = new ArrayList<>();

	@Option(name = "--cached"
	private boolean cached;

	@Override
	protected void run() {
		try (Git git = new Git(db)) {
			RestoreCommand command = git.restore();
			command.setCached(cached);
			for (String p : paths) {
				command.addFilepattern(p);
			}
			command.call();
		} catch (GitAPIException e) {
			throw die(e.getMessage()
		}
	}
}
