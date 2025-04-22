package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public class AdditionalRefsNode extends RepositoryTreeNode<Repository> {

	public AdditionalRefsNode(RepositoryTreeNode parent, Repository repository) {
		super(parent, RepositoryTreeNodeType.ADDITIONALREFS, repository,
				repository);
	}

}
