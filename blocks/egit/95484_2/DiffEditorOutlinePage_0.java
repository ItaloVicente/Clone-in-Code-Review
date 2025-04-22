	private void openFolder(ISelection currentSelection) {
		if (currentSelection instanceof IStructuredSelection) {
			Object currentNode = ((IStructuredSelection) currentSelection)
					.getFirstElement();
			if (currentNode instanceof DiffContentProvider.Folder) {
				TreeViewer viewer = getTreeViewerChecked();
				if (viewer != null) {
					viewer.setExpandedState(currentNode,
							!viewer.getExpandedState(currentNode));
				}
			}
		}
	}

