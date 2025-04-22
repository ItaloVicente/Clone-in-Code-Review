package org.eclipse.jgit.pgm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.CherryPickCommand;
import org.eclipse.jgit.api.CherryPickResult;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
public class CherryPick extends TextBuiltin {
	@Argument(required = true
	private List<String> commits = new ArrayList<String>();

	@Option(name = "--no-commit"
	private boolean noCommit;

	@Override
	protected void run() throws Exception {
		try (Git git = new Git(db)) {
			CherryPickCommand cherryPick = git.cherryPick();
			for (String commit : commits) {
				cherryPick.include(ObjectId.fromString(commit));
			}
			cherryPick.setNoCommit(noCommit);

			CherryPickResult result = cherryPick.call();
			if (result.getStatus() == CherryPickResult.CherryPickStatus.OK) {
				Rebase.success(outw);
			} else {
				Rebase.error(errw
						result.getFailingPaths());
			}
		}
	}
}
