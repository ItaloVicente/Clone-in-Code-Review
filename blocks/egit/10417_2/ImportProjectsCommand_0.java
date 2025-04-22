		List<RepositoryTreeNode> selectedNodes = getSelectedNodes(event);
		if (selectedNodes == null || selectedNodes.isEmpty()) {
			MessageDialog
					.openError(Display.getDefault().getActiveShell(),
					UIText.ImportProjectsWrongSelection,
					UIText.ImportProjectsSelectionInRepositoryRequired);
			return null;
		}
		if (!(((List) selectedNodes).get(0) instanceof RepositoryTreeNode)) {
			MessageDialog.openError(Display.getDefault().getActiveShell(),
					UIText.ImportProjectsWrongSelection,
					UIText.ImportProjectsSelectionInRepositoryRequired);
			return null;
		}
		RepositoryTreeNode node = selectedNodes.get(0);
