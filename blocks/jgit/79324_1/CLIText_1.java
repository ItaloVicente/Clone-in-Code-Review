
package org.eclipse.jgit.pgm;

import java.text.MessageFormat;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.kohsuke.args4j.Option;

@Command(common = true
class Clean extends TextBuiltin {
	@Option(name = "-d"
	private boolean dirs = false;

	@Override
	protected void run() throws Exception {
		try (Git git = new Git(db)) {
			Set<String> removedFiles = git.clean().setCleanDirectories(dirs)
					.call();
			for (String removedFile : removedFiles) {
				outw.println(MessageFormat.format(CLIText.get().removing
						removedFile));
			}
		}
	}
}
