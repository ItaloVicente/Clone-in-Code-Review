					MessageDialog.openError(
							shell,
							IDEWorkbenchMessages.IDEApplication_workspaceCannotLockTitle,
							NLS.bind(IDEWorkbenchMessages.IDEApplication_workspaceCannotLockMessage, workspaceDirectory.getAbsolutePath()));
				} else {
					MessageDialog.openError(
