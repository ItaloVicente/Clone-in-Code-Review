package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public class StashNode extends RepositoryTreeNode<Repository> {

	public StashNode(RepositoryTreeNode parent, Repository repository) {
		super(parent, RepositoryTreeNodeType.STASH, repository, repository);
	}
}
