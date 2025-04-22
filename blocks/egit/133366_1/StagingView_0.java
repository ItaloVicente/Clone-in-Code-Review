package org.eclipse.egit.ui.internal.selection;

import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNodeType;
import org.eclipse.jgit.lib.Repository;

public class RepositoryVirtualNode extends RepositoryTreeNode<Repository> {

	public RepositoryVirtualNode(RepositoryTreeNode parent, Repository repository) {
		super(parent, RepositoryTreeNodeType.REPO, repository, repository);
	}

}
