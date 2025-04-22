	private void handleException(Exception e, RepositoryTreeNode parentNode) {
		Activator.handleError(e.getMessage(), e, false);
		new RepositoryTreeNode<String>(parentNode,
				RepositoryTreeNodeType.ERROR, parentNode.getRepository(),
				UIText.RepositoriesViewContentProvider_ExceptionNodeText);
	}

