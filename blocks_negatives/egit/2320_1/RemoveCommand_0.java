				String name = org.eclipse.egit.core.Activator.getDefault()
						.getRepositoryUtil()
						.getRepositoryName(selectedNodes.get(0).getObject());
				String message = NLS.bind(
						UIText.RemoveCommand_DeleteConfirmMultiMessage, name);
				if (!MessageDialog.openConfirm(getShell(event), title, message))
					return;
