		List<FileNode> selectedNodes = getSelectedNodes(event);
		if (selectedNodes.isEmpty() || selectedNodes.get(0).getRepository() == null)
			return null;

		Repository repository = selectedNodes.get(0).getRepository();
		IPath workTreePath = new Path(repository.getWorkTree().getAbsolutePath());

		AddCommand addCommand = new Git(repository).add();

		Collection<IPath> paths = getSelectedFileAndFolderPaths(event);
		for (IPath path : paths) {
