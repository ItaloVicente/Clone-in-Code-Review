						String message = NLS.bind(WorkbenchMessages.ResetPerspective_message,
								descriptor.getLabel());
						boolean result = MessageDialog.open(MessageDialog.QUESTION,
								activeWorkbenchWindow.getShell(),
								WorkbenchMessages.ResetPerspective_title, message, SWT.SHEET);
						if (result) {
