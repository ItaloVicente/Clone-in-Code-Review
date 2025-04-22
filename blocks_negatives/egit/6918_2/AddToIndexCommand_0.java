		AddCommand addCommand = null;
		List selectedNodes = getSelectedNodes(event);
		for (Object selectedNode : selectedNodes) {
			RepositoryTreeNode node = (RepositoryTreeNode) selectedNode;
			if (addCommand == null)
				addCommand = new Git(node.getRepository()).add();
			Repository repository = node.getRepository();
			IPath path;
			if (node.getType().equals(RepositoryTreeNodeType.FOLDER))
				path = new Path(((FolderNode) node).getObject()
						.getAbsolutePath());
			else if (node.getType().equals(RepositoryTreeNodeType.FILE))
				path = new Path(((FileNode) node).getObject().getAbsolutePath());
			else
				path = new Path(repository.getWorkTree().getAbsolutePath());
