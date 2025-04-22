
package org.eclipse.jgit.pgm;

import org.eclipse.jgit.api.Git;
import org.kohsuke.args4j.Option;

@Command(common = true
class Clean extends TextBuiltin {
	@Option(name = "-d"
	private boolean dirs = false;

	@Override
	protected void run() throws Exception {
		try (Git git = new Git(db)) {
			git.clean().setCleanDirectories(dirs).call();
		}
	}
}
