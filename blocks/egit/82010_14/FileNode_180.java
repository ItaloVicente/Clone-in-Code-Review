package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public class FetchNode extends RepositoryTreeNode<String> {

	public FetchNode(RepositoryTreeNode parent, Repository repository,
			String fetchUri) {
		super(parent, RepositoryTreeNodeType.FETCH, repository, fetchUri);
	}

}
