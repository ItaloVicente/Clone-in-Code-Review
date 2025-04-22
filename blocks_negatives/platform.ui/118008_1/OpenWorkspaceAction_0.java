			MessageDialog
					.openError(
							window.getShell(),
							IDEWorkbenchMessages.OpenWorkspaceAction_errorTitle,
							NLS
									.bind(
											IDEWorkbenchMessages.OpenWorkspaceAction_errorMessage,
											PROP_VM));
