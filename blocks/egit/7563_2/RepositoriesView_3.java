	private RepositoryTreeNode getNodeForPath(Repository repository, String repoRelativePath) {
		RepositoryTreeNode currentNode = null;
		ITreeContentProvider cp = (ITreeContentProvider) getCommonViewer()
				.getContentProvider();
		for (Object repo : cp.getElements(getCommonViewer().getInput())) {
			RepositoryTreeNode node = (RepositoryTreeNode) repo;
			if (repository.getDirectory().equals(
					((Repository) node.getObject()).getDirectory())) {
				for (Object child : cp.getChildren(node)) {
					RepositoryTreeNode childNode = (RepositoryTreeNode) child;
					if (childNode.getType() == RepositoryTreeNodeType.WORKINGDIR) {
						currentNode = childNode;
						break;
					}
				}
				break;
			}
		}

		IPath relPath = new Path(repoRelativePath);

		for (String segment : relPath.segments())
			for (Object child : cp.getChildren(currentNode)) {
				@SuppressWarnings("unchecked")
				RepositoryTreeNode<File> childNode = (RepositoryTreeNode<File>) child;
				if (childNode.getObject().getName().equals(segment)) {
					currentNode = childNode;
					break;
				}
			}

		return currentNode;
	}

