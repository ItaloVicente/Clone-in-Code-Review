package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

public class SymbolicRefNode extends RepositoryTreeNode<Ref> {

	public SymbolicRefNode(RepositoryTreeNode parent, Repository repository,
			Ref ref) {
		super(parent, RepositoryTreeNodeType.SYMBOLICREF, repository, ref);
	}

}
