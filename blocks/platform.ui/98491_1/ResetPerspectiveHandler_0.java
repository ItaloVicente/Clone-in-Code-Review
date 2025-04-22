						final String[] options = { WorkbenchMessages.ResetPerspective_buttonLabel,
								IDialogConstants.NO_LABEL };

						int result = MessageDialog.open(MessageDialog.CONFIRM, activeWorkbenchWindow.getShell(),
								WorkbenchMessages.ResetPerspective_title, message, SWT.SHEET, options);

						if (result == RETURNCODE_RESET_PERSPECTIVE) {
