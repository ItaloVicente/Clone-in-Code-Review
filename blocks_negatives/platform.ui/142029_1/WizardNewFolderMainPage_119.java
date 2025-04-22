			IDEWorkbenchPlugin.log(getClass(),
					"createNewFolder()", e.getTargetException()); //$NON-NLS-1$
			MessageDialog
					.open(MessageDialog.ERROR,
							getContainer().getShell(),
							IDEWorkbenchMessages.WizardNewFolderCreationPage_internalErrorTitle,
							NLS
									.bind(
											IDEWorkbenchMessages.WizardNewFolder_internalError,
											e.getTargetException().getMessage()), SWT.SHEET);
