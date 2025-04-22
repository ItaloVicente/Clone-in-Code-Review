package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public class SubmodulesNode extends RepositoryTreeNode<Repository> {

	public SubmodulesNode(RepositoryTreeNode parent, Repository repository) {
		super(parent, RepositoryTreeNodeType.SUBMODULES, repository, repository);
	}
}
