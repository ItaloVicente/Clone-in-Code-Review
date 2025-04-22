package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public class PushNode extends RepositoryTreeNode<String> {

	public PushNode(RepositoryTreeNode parent, Repository repository,
			String pushUri) {
		super(parent, RepositoryTreeNodeType.PUSH, repository, pushUri);
	}

}
