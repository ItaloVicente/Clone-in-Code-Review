package org.eclipse.egit.ui.internal.repository.tree;

public class RepositoryGroupNode extends RepositoryTreeNode<String> {

	private RepositoryGroup group;

	public RepositoryGroupNode(RepositoryTreeNode parent,
			RepositoryGroup group) {
		super(parent, RepositoryTreeNodeType.REPOGROUP, null, group.getName());
		this.group = group;
	}

	public boolean hasChildren() {
		return group.hasRepositories();
	}

	public RepositoryGroup getGroup() {
		return group;
	}

}
