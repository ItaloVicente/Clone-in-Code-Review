package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public class RepositoryNode extends RepositoryTreeNode<Repository> {

	public RepositoryNode(RepositoryTreeNode parent, Repository repository) {
		super(parent, RepositoryTreeNodeType.REPO, repository, repository);
	}

}
