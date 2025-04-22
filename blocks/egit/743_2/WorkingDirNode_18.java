package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Repository;

public class TagsNode extends RepositoryTreeNode<Repository> {

	public TagsNode(RepositoryTreeNode parent, Repository repository) {
		super(parent, RepositoryTreeNodeType.TAGS, repository, repository);
	}

}
