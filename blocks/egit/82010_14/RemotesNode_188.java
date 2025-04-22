package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public class RemoteTrackingNode extends RepositoryTreeNode<Repository> {

	public RemoteTrackingNode(RepositoryTreeNode parent, Repository repository) {
		super(parent, RepositoryTreeNodeType.REMOTETRACKING, repository,
				repository);
	}

}
