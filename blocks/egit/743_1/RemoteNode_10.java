package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public class RemoteBranchesNode extends RepositoryTreeNode<Repository> {

	public RemoteBranchesNode(RepositoryTreeNode parent, Repository repository) {
		super(parent, RepositoryTreeNodeType.REMOTEBRANCHES, repository,
				repository);
	}

}
