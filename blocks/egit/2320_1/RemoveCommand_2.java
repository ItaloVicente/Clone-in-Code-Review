				Repository repository = selectedNodes.get(0).getObject();
				if (repository.isBare()) {
					String title = UIText.RemoveCommand_ConfirmDeleteBareRepositoryTitle;
					String message = NLS
							.bind(
									UIText.RemoveCommand_ConfirmDeleteBareRepositoryMessage,
									repository.getDirectory().getPath());
					if (!MessageDialog.openConfirm(getShell(event), title,
							message))
						return;
				} else {
					DeleteRepositoryConfirmDialog dlg = new DeleteRepositoryConfirmDialog(
							getShell(event), repository);
					if (dlg.open() != Window.OK)
						return;
					deleteWorkingDir = dlg.shouldDeleteWorkingDir();
				}
