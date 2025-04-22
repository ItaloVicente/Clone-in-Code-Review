		IRunnableWithProgress op = monitor -> {
			AbstractOperation op1;
			op1 = new CreateFolderOperation(
				newFolderHandle, linkTargetPath, createVirtualFolder, filterList,
				IDEWorkbenchMessages.WizardNewFolderCreationPage_title);
			try {
				op1.execute(monitor, WorkspaceUndoUtil
					.getUIInfoAdapter(getShell()));
			} catch (final ExecutionException e) {
				getContainer().getShell().getDisplay().syncExec(
						() -> {
							if (e.getCause() instanceof CoreException) {
								ErrorDialog
										.openError(
												getContainer()
														.getShell(), // Was Utilities.getFocusShell()
												IDEWorkbenchMessages.WizardNewFolderCreationPage_errorTitle,
												null, // no special message
												((CoreException) e
														.getCause())
														.getStatus());
							} else {
								IDEWorkbenchPlugin
										.log(
												getClass(),
												"createNewFolder()", e.getCause()); //$NON-NLS-1$
								MessageDialog
										.openError(
												getContainer()
														.getShell(),
												IDEWorkbenchMessages.WizardNewFolderCreationPage_internalErrorTitle,
												NLS
														.bind(
																IDEWorkbenchMessages.WizardNewFolder_internalError,
																e
																		.getCause()
																		.getMessage()));
							}
						});
