			RepositoryTreeNode selectedNode = selectedNodes.get(0);
			if (selectedNode.getType() == RepositoryTreeNodeType.REPO
					|| selectedNode.getType() == RepositoryTreeNodeType.FILE
					|| selectedNode.getType() == RepositoryTreeNodeType.FOLDER)
				nodeToShow = selectedNode;
			else
				nodeToShow = null;
		} else
			nodeToShow = null;
		if (nodeToShow == null)
			for (RepositoryTreeNode node : getSelectedNodes(event)) {
				if (repo == null)
					repo = node.getRepository();
				if (repo != node.getRepository())
					throw new ExecutionException(
							UIText.AbstractHistoryCommanndHandler_NoUniqueRepository);
				if (node.getType() == RepositoryTreeNodeType.FOLDER) {
					fileList.add(((FolderNode) node).getObject());
				}
				if (node.getType() == RepositoryTreeNodeType.FILE) {
					fileList.add(((FileNode) node).getObject());
				}
			}
		final Repository repoToShow = repo;
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				try {
					IHistoryView part = (IHistoryView) PlatformUI
							.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().showView(IHistoryView.VIEW_ID);
					if (nodeToShow != null)
