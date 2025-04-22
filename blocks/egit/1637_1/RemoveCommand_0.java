		final List<RepositoryNode> selectedNodes;
		try {
			selectedNodes = getSelectedNodes(event);
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, true);
			return;
		}

		if (delete) {
			String title = UIText.RemoveCommand_DeleteConfirmTitle;
			if (selectedNodes.size() > 1) {
				String message = NLS.bind(
						UIText.RemoveCommand_DeleteConfirmSingleMessage,
						Integer.valueOf(selectedNodes.size()));
				if (!MessageDialog.openConfirm(getShell(event), title, message))
					return;
			} else if (selectedNodes.size() == 1) {
				String name = org.eclipse.egit.core.Activator.getDefault()
						.getRepositoryUtil()
						.getRepositoryName(selectedNodes.get(0).getObject());
				String message = NLS.bind(
						UIText.RemoveCommand_DeleteConfirmMultiMessage, name);
				if (!MessageDialog.openConfirm(getShell(event), title, message))
					return;
