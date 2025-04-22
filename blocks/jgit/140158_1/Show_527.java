
package org.eclipse.jgit.pgm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RmCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StopOptionHandler;

@Command(usage = "usage_StopTrackingAFile"
class Rm extends TextBuiltin {
	@Argument(metaVar = "metaVar_path"
	@Option(name = "--"
	private List<String> paths = new ArrayList<>();

	@Override
	protected void run() {
		try (Git git = new Git(db)) {
			RmCommand command = git.rm();
			for (String p : paths) {
				command.addFilepattern(p);
			}
			command.call();
		} catch (GitAPIException e) {
			throw die(e.getMessage()
		}
	}
}
