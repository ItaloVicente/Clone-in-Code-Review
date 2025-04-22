package org.eclipse.egit.ui.internal.repository.tree;

public class RepositoryGroupNode extends RepositoryTreeNode<String> {

	private boolean hasChildren;

	public RepositoryGroupNode(RepositoryTreeNode parent, String groupName,
			boolean hasChildren) {
		super(parent, RepositoryTreeNodeType.REPOGROUP, null, groupName);
		this.hasChildren = hasChildren;
	}

	public boolean hasChildren() {
		return hasChildren;
	}

}
