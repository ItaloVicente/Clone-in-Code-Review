package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public class BranchesNode extends RepositoryTreeNode<Repository> {

	public BranchesNode(RepositoryTreeNode parent, Repository repository) {
		super(parent, RepositoryTreeNodeType.BRANCHES, repository, repository);
	}

}
