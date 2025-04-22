	@Override
	public String getText(Object element) {
		if (!(element instanceof RepositoryTreeNode))
			return null;

		RepositoryTreeNode node = (RepositoryTreeNode) element;

		return getSimpleText(node);
