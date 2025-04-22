package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.DescribeCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
class Describe extends TextBuiltin {

	@Argument(index = 0
	private ObjectId tree;

	@Option(name = "--long"
	private boolean longDesc;

	@Option(name = "--match"
	private List<String> patterns = new ArrayList<>();

	@Override
	protected void run() {
		try (Git git = new Git(db)) {
			DescribeCommand cmd = git.describe();
			if (tree != null) {
				cmd.setTarget(tree);
			}
			cmd.setLong(longDesc);
			cmd.setMatch(patterns.toArray(new String[0]));
			String result = null;
			try {
				result = cmd.call();
			} catch (RefNotFoundException e) {
				throw die(CLIText.get().noNamesFound
			}
			if (result == null) {
				throw die(CLIText.get().noNamesFound);
			}

			outw.println(result);
		} catch (IOException | InvalidPatternException | GitAPIException e) {
			throw die(e.getMessage()
		}
	}
}
