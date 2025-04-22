package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public class LocalBranchesNode extends RepositoryTreeNode<Repository> {

	public LocalBranchesNode(RepositoryTreeNode parent, Repository repository) {
		super(parent, RepositoryTreeNodeType.LOCALBRANCHES, repository,
				repository);
	}

}
