				if (p.canActivate(editor)) {
					p.activate(editor);
				} else {
					MessageDialog.open(MessageDialog.INFORMATION, getShell(),
							WorkbenchMessages.CannotActivateEditor_title,
							WorkbenchMessages.CannotActivateEditor_message, SWT.DIALOG_TRIM);
				}
