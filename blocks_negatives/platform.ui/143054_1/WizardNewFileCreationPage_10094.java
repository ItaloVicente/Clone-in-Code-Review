			IDEWorkbenchPlugin.log(getClass(),
					"createNewFile()", e.getTargetException()); //$NON-NLS-1$
			MessageDialog
					.open(MessageDialog.ERROR,
							getContainer().getShell(),
							IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorTitle,
							NLS
									.bind(
											IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorMessage,
											e.getTargetException().getMessage()), SWT.SHEET);
