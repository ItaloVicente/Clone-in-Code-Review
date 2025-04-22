package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

public class RefNode extends RepositoryTreeNode<Ref> {

	public RefNode(RepositoryTreeNode parent, Repository repository, Ref ref) {
		super(parent, RepositoryTreeNodeType.REF, repository, ref);
	}

}
