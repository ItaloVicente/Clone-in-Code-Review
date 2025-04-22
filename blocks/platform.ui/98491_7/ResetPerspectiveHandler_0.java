						String message = NLS.bind(WorkbenchMessages.ResetPerspective_message, descriptor.getLabel());

						int result = MessageDialog.open(MessageDialog.CONFIRM, activeWorkbenchWindow.getShell(),
								WorkbenchMessages.ResetPerspective_title, message, SWT.SHEET, WorkbenchMessages.ResetPerspective_buttonLabel,
								IDialogConstants.NO_LABEL);

						if (result == Window.OK) {
