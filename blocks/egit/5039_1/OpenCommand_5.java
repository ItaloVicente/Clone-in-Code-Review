package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class StashedCommitNode extends RepositoryTreeNode<RevCommit> {

	private final int index;

	public StashedCommitNode(RepositoryTreeNode parent, Repository repository,
			int index, RevCommit commit) {
		super(parent, RepositoryTreeNodeType.STASHED_COMMIT, repository, commit);
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
