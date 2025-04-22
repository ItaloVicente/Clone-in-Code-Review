
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RebaseCommand;
import org.eclipse.jgit.api.RebaseResult;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason;
import org.eclipse.jgit.util.io.ThrowingPrintWriter;
import org.kohsuke.args4j.Argument;

@Command(common = false
class Rebase extends TextBuiltin {
	@Argument(required = true
	private String upstream;

	@Override
	protected void run() throws Exception {
		try (Git git = new Git(db)) {
			RebaseCommand rebase = git.rebase();
			rebase.setProgressMonitor(new TextProgressMonitor(outw));
			rebase.setUpstream(upstream);
			RebaseResult result = rebase.call();
			if (result.getStatus().isSuccessful()) {
				success(outw);
			} else {
				error(errw
						result.getFailingPaths());
			}
		}
	}

	@SuppressWarnings("nls")
	static void success(ThrowingPrintWriter writer) throws IOException {
		writer.println("Success!");
	}

	@SuppressWarnings("nls")
	static void error(ThrowingPrintWriter writer
			List<String> conflicts
			throws IOException {
		writer.println("Error! " + reason);
		if (conflicts != null && !conflicts.isEmpty()) {
			writer.println("Conflicts:");
			for (String conflict : conflicts) {
				writer.println("\t" + conflict);
			}
		}
		if (!failingPaths.isEmpty()) {
			writer.println("Failures:");
			for (Map.Entry<String
					.entrySet()) {
				writer.println("\t" + conflict.getKey() + ": "
						+ conflict.getValue());
			}
		}
	}
}
