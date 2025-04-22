
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.kohsuke.args4j.Option;

@Command(common = true
class Clean extends TextBuiltin {
	@Option(name = "-d"
	private boolean dirs = false;

	@Option(name = "--force"
			"-f" }
	private boolean force = false;

	@Option(name = "--dryRun"
	private boolean dryRun = false;

	@Override
	protected void run() {
		try (Git git = new Git(db)) {
			boolean requireForce = git.getRepository().getConfig()
					.getBoolean("clean"
			if (requireForce && !(force || dryRun)) {
				throw die(CLIText.fatalError(CLIText.get().cleanRequireForce));
			}
			Set<String> removedFiles = git.clean().setCleanDirectories(dirs)
					.setDryRun(dryRun).call();
			for (String removedFile : removedFiles) {
				outw.println(MessageFormat.format(CLIText.get().removing
						removedFile));
			}
		} catch (NoWorkTreeException | GitAPIException | IOException e) {
			throw die(e.getMessage()
		}
	}
}
