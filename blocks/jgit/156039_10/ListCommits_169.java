package org.eclipse.jgit.niofs.internal.op.commands;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.revwalk.RevCommit;

public class GetTreeFromRef {

	private final Git git;
	private final String treeRefName;

	public GetTreeFromRef(final Git git
		this.git = git;
		this.treeRefName = treeRefName;
	}

	public ObjectId execute() {
		final RevCommit commit = git.getLastCommit(treeRefName);
		if (commit == null) {
			return null;
		}
		return commit.getTree().getId();
	}
}
