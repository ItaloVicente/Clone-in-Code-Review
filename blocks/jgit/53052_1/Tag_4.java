package org.eclipse.jgit.pgm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RevertCommand;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
public class Revert extends TextBuiltin {
	@Argument(required = true
	private List<String> commits = new ArrayList<String>();

	@Option(name = "--no-commit"
	private boolean noCommit;

	@Override
	protected void run() throws Exception {
		try (Git git = new Git(db)) {
			RevertCommand revert = git.revert();
			for (String commit : commits) {
				revert.include(ObjectId.fromString(commit));
			}
			revert.setNoCommit(noCommit);

			try {
				RevCommit result = revert.call();
				if (result != null) {
					Rebase.success(outw);
				} else {
					Rebase.error(errw
							.getMergeStatus()
							.getCheckoutConflicts()
							.getFailingPaths());
				}
			} catch (Exception e) {
				throw die(e.getMessage()
			}
		}
	}
}
