				MessageDialog
						.open(MessageDialog.ERROR,
								getContainer().getShell(),
								IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorTitle,
								NLS
										.bind(
												IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorMessage,
												e.getMessage()), SWT.SHEET);
