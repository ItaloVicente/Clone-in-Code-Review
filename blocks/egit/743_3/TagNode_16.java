package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public class SymbolicRefsNode extends RepositoryTreeNode<Repository> {

	public SymbolicRefsNode(RepositoryTreeNode parent, Repository repository) {
		super(parent, RepositoryTreeNodeType.SYMBOLICREFS, repository,
				repository);
	}

}
