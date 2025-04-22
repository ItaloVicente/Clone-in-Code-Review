package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

public class AdditionalRefNode extends RepositoryTreeNode<Ref> {

	public AdditionalRefNode(RepositoryTreeNode parent, Repository repository,
			Ref ref) {
		super(parent, RepositoryTreeNodeType.ADDITIONALREF, repository, ref);
	}

}
