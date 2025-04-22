
package org.eclipse.jgit.pgm;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.kohsuke.args4j.Option;

@Command(common = true
class Gc extends TextBuiltin {
	@Option(name = "--aggressive"
	private boolean aggressive;

	@Option(name = "--preserve-oldpacks"
	private boolean preserveOldPacks;

	@Option(name = "--prune-preserved"
	private boolean prunePreserved;

	@Override
	protected void run() {
		Git git = Git.wrap(db);
		try {
			git.gc().setAggressive(aggressive)
					.setPreserveOldPacks(preserveOldPacks)
					.setPrunePreserved(prunePreserved)
					.setProgressMonitor(new TextProgressMonitor(errw)).call();
		} catch (GitAPIException e) {
			throw die(e.getMessage()
		}
	}
}
