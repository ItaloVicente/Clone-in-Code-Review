
package org.eclipse.jgit.pgm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.RestOfArgumentsHandler;

@Command(common = true
class Reset extends TextBuiltin {

	@Option(name = "--soft"
	private boolean soft = false;

	@Option(name = "--mixed"
	private boolean mixed = false;

	@Option(name = "--hard"
	private boolean hard = false;

	@Argument(required = false
	private String commit;

	@Argument(required = false
	@Option(name = "--"
	private List<String> paths = new ArrayList<>();

	@Override
	protected void run() {
		try (Git git = new Git(db)) {
			ResetCommand command = git.reset();
			command.setRef(commit);
			if (paths.size() > 0) {
				for (String path : paths) {
					command.addPath(path);
				}
			} else {
				ResetType mode = null;
				if (soft) {
					mode = selectMode(mode
				}
				if (mixed) {
					mode = selectMode(mode
				}
				if (hard) {
					mode = selectMode(mode
				}
				if (mode == null) {
					throw die(CLIText.get().resetNoMode);
				}
				command.setMode(mode);
			}
			command.call();
		} catch (GitAPIException e) {
			throw die(e.getMessage()
		}
	}

	private static ResetType selectMode(ResetType mode
		if (mode != null)
			throw die("reset modes are mutually exclusive
		return want;
	}
}
