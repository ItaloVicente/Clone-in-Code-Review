
package org.eclipse.jgit.pgm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
class Add extends TextBuiltin {

	@Option(name = "--update"
	private boolean update = false;

	@Argument(required = true
	private List<String> filepatterns = new ArrayList<>();

	@Override
	protected void run() throws Exception {
		try (Git git = new Git(db)) {
			AddCommand addCmd = git.add();
			addCmd.setUpdate(update);
			for (String p : filepatterns)
				addCmd.addFilepattern(p);
			addCmd.call();
		} catch (GitAPIException e) {
			throw die(e.getMessage()
		}
	}
}
