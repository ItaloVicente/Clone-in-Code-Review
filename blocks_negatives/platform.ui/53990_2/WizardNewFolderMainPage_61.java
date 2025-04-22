		IRunnableWithProgress op = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) {
				AbstractOperation op;
				op = new CreateFolderOperation(
					newFolderHandle, linkTargetPath, createVirtualFolder, filterList,
					IDEWorkbenchMessages.WizardNewFolderCreationPage_title);
				try {
					op.execute(monitor, WorkspaceUndoUtil
						.getUIInfoAdapter(getShell()));
				} catch (final ExecutionException e) {
					getContainer().getShell().getDisplay().syncExec(
							new Runnable() {
								@Override
								public void run() {
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
								}
							});
				}
