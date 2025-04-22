package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public class RemotesNode extends RepositoryTreeNode<Repository> {

	public RemotesNode(RepositoryTreeNode parent, Repository repository) {
		super(parent, RepositoryTreeNodeType.REMOTES, repository, repository);
	}

}
