		if (delete)
			try {
				List<RepositoryNode> selectedNodes = getSelectedNodes(event);
				String title = UIText.RemoveCommand_DeleteConfirmTitle;
				if (selectedNodes.size() > 1) {
					String message = NLS.bind(
							UIText.RemoveCommand_DeleteConfirmSingleMessage,
							Integer.valueOf(selectedNodes.size()));
					if (!MessageDialog.openConfirm(getShell(event), title,
							message))
						return;
				} else if (selectedNodes.size() == 1) {
					String name = org.eclipse.egit.core.Activator.getDefault()
							.getRepositoryUtil().getRepositoryName(
									selectedNodes.get(0).getObject());
					String message = NLS.bind(
							UIText.RemoveCommand_DeleteConfirmMultiMessage,
							name);
					if (!MessageDialog.openConfirm(getShell(event), title,
							message))
						return;
				}
			} catch (ExecutionException e) {
				Activator.handleError(e.getMessage(), e, false);
				return;
			}

