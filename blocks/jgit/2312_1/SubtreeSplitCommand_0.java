
package org.eclipse.jgit.pgm;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.jgit.api.SubtreeSplitCommand;
import org.eclipse.jgit.api.SubtreeSplitResult;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.subtree.SubtreeContext;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;

@Command
class Subtree extends TextBuiltin {

	@Option(name = "--rewrite")
	private boolean rewrite;

	@Option(name = "--rewrite-all")
	private boolean rewriteAll;

	@Argument(index = 0)
	private String subtreeCmd;

	@Argument(index = 1)
	private List<String> args;

	@Override
	protected void run() throws Exception {

		if ("split".equals(subtreeCmd)) {
			SubtreeSplitCommand split = new SubtreeSplitCommand(db);
			if (args != null) {
				for (String path : args) {
					split.addSplitPath(path);
				}
			}

			ObjectId head = db.resolve("HEAD");
			if (rewriteAll) {
				split.setRewriteAll();
			} else if (rewrite) {
				split.addCommitToRewrite(head);
			}
			split.setStart(head);

			SubtreeSplitResult result = split.call();
			if (rewrite || rewriteAll) {
				out.println(result.getRewrittenCommits().get(head).name());
			}
			for (SubtreeContext c : result.getSubtreeContexts()) {
				out.println(c.getSplitCommit(head).name() + " " + c.getId());
			}
		} else {
			throw new CmdLineException(MessageFormat.format(
					CLIText.get().notAJgitCommand
		}

	}
}
