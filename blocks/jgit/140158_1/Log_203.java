
package org.eclipse.jgit.pgm;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
class Init extends TextBuiltin {
	@Option(name = "--bare"
	private boolean bare;

	@Argument(index = 0
	private String directory;

	@Override
	protected final boolean requiresRepository() {
		return false;
	}

	@Override
	protected void run() {
		InitCommand command = Git.init();
		command.setBare(bare);
		if (gitdir != null) {
			command.setDirectory(new File(gitdir));
		}
		if (directory != null) {
			command.setDirectory(new File(directory));
		}
		Repository repository;
		try {
			repository = command.call().getRepository();
			outw.println(MessageFormat.format(
					CLIText.get().initializedEmptyGitRepositoryIn
					repository.getDirectory().getAbsolutePath()));
		} catch (GitAPIException | IOException e) {
			throw die(e.getMessage()
		}
	}
}
