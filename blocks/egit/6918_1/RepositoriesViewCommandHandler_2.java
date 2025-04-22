	protected Collection<IPath> getSelectedFileAndFolderPaths(ExecutionEvent event) throws ExecutionException {
		Collection<IPath> paths = new ArrayList<IPath>();
		for (Object selectedNode : getSelectedNodes(event)) {
			RepositoryTreeNode node = (RepositoryTreeNode) selectedNode;
			Repository repository = node.getRepository();
			IPath path;
			if (node.getType().equals(RepositoryTreeNodeType.FOLDER))
				path = new Path(((FolderNode) node).getObject()
						.getAbsolutePath());
			else if (node.getType().equals(RepositoryTreeNodeType.FILE))
				path = new Path(((FileNode) node).getObject().getAbsolutePath());
			else
				path = new Path(repository.getWorkTree().getAbsolutePath());
			paths.add(path);
		}
		return paths;
	}
