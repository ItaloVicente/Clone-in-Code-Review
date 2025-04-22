package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public class LocalNode extends RepositoryTreeNode<Repository> {

	public LocalNode(RepositoryTreeNode parent, Repository repository) {
		super(parent, RepositoryTreeNodeType.LOCAL, repository,
				repository);
	}

}
