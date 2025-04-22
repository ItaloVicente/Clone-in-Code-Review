		IStructuredSelection sel = (IStructuredSelection) branchTree
				.getSelection();
		if (sel.size() != 1)
			return null;
		RepositoryTreeNode node = (RepositoryTreeNode) sel.getFirstElement();
		if (node.getType() == RepositoryTreeNodeType.REF) {
			return ((Ref) node.getObject()).getName();
