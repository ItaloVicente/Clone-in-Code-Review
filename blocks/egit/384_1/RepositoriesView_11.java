		if (sel.size() > 1)
			return;

		final RepositoryTreeNode node = (RepositoryTreeNode) sel
				.getFirstElement();

		if (node.getType() == RepositoryTreeNodeType.REF) {

			final Ref ref = (Ref) node.getObject();
