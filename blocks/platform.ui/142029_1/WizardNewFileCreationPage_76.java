				getContainer().getShell().getDisplay().syncExec(() -> {
					if (e.getCause() instanceof CoreException) {
						ErrorDialog.openError(getContainer().getShell(), // Was
								IDEWorkbenchMessages.WizardNewFileCreationPage_errorTitle, null, // no special
								((CoreException) e.getCause()).getStatus());
					} else {
						IDEWorkbenchPlugin.log(getClass(), "createNewFile()", e.getCause()); //$NON-NLS-1$
						MessageDialog.openError(getContainer().getShell(),
								IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorTitle,
								NLS.bind(IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorMessage,
										e.getCause().getMessage()));
					}
				});
