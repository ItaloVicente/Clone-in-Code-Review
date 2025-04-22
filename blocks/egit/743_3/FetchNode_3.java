package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public class ErrorNode extends RepositoryTreeNode<String> {

	public ErrorNode(RepositoryTreeNode parent, Repository repository,
			String error) {
		super(parent, RepositoryTreeNodeType.ERROR, repository, error);
	}

}
