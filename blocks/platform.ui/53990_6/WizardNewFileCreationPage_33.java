		IRunnableWithProgress op = monitor -> {
			CreateFileOperation op1 = new CreateFileOperation(newFileHandle,
					linkTargetPath, initialContents,
					IDEWorkbenchMessages.WizardNewFileCreationPage_title);
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
														.getShell(), // Was
												IDEWorkbenchMessages.WizardNewFileCreationPage_errorTitle,
												null, // no special
												((CoreException) e
														.getCause())
														.getStatus());
							} else {
								IDEWorkbenchPlugin
										.log(
												getClass(),
												"createNewFile()", e.getCause()); //$NON-NLS-1$
								MessageDialog
										.openError(
												getContainer()
														.getShell(),
												IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorTitle,
												NLS
														.bind(
																IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorMessage,
																e
																		.getCause()
																		.getMessage()));
							}
						});
