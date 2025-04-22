package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public class RemoteNode extends RepositoryTreeNode<String> {

	public RemoteNode(RepositoryTreeNode parent, Repository repository,
			String remoteName) {
		super(parent, RepositoryTreeNodeType.REMOTE, repository, remoteName);
	}

}
