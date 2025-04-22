package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public class WorkingDirNode extends RepositoryTreeNode<Repository> {

	public WorkingDirNode(RepositoryTreeNode parent, Repository repository) {
		super(parent, RepositoryTreeNodeType.WORKINGDIR, repository, repository);
	}

}
