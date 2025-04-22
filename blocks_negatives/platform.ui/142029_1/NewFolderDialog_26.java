				IDEWorkbenchPlugin.log(getClass(),
						"createNewFolder", exception.getTargetException()); //$NON-NLS-1$
				MessageDialog
						.openError(
								getShell(),
								IDEWorkbenchMessages.NewFolderDialog_errorTitle,
								NLS
										.bind(
												IDEWorkbenchMessages.NewFolderDialog_internalError,
												exception.getTargetException()
														.getMessage()));
