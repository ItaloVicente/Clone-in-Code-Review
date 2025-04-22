		if (doSetSelection) {
			RepositoriesViewContentProvider cp = (RepositoriesViewContentProvider) tv
					.getContentProvider();
			RepositoryTreeNode currentNode = null;
			Object[] repos = cp.getElements(tv.getInput());
			for (Object repo : repos) {
				RepositoryTreeNode node = (RepositoryTreeNode) repo;
				if (mapping.getRepository().getDirectory().equals(
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

			IPath relPath = new Path(mapping.getRepoRelativePath(resource));

			for (String segment : relPath.segments()) {
				for (Object child : cp.getChildren(currentNode)) {
					RepositoryTreeNode<File> childNode = (RepositoryTreeNode<File>) child;
					if (childNode.getObject().getName().equals(segment)) {
