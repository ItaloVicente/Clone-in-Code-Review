
package org.eclipse.jgit.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.subtree.PathBasedContext;
import org.eclipse.jgit.subtree.SubtreeSplitter;

public class SubtreeSplitCommand extends GitCommand<SubtreeSplitResult> {

	private ObjectId start;

	private ArrayList<PathBasedContext> pathContexts = new ArrayList<PathBasedContext>();

	private Set<ObjectId> toRewrite = new HashSet<ObjectId>();

	public SubtreeSplitCommand(Repository repo) {
		super(repo);
	}

	public void setStart(ObjectId start) {
		this.start = start;
	}

	public void addCommitToRewrite(ObjectId commit) {
		if (this.toRewrite != SubtreeSplitter.REWRITE_ALL) {
			this.toRewrite.add(commit);
		}
	}

	public void setRewriteAll() {
		this.toRewrite = SubtreeSplitter.REWRITE_ALL;
	}
	public void addSplitPath(String path) {
		while (path.endsWith("/")) {
			path = path.substring(0
		}
		while (path.startsWith("/")) {
			path = path.substring(1);
		}
		pathContexts.add(new PathBasedContext(path
	}

	public SubtreeSplitResult call() throws Exception {
		checkCallable();

		RevWalk walk = new RevWalk(repo);
		try {
			SubtreeSplitter splitter = new SubtreeSplitter(this.repo
			splitter.splitSubtrees(start
			setCallable(false);
			return new SubtreeSplitResult(
					splitter.getSubtreeContexts()
					splitter.getRewrittenCommits());
		} finally {
			walk.release();
		}
	}

}
