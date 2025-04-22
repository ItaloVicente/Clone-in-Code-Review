				MessageDialog dialog = new MessageDialog(
						messageShell,
						IDEWorkbenchMessages.CopyFilesAndFoldersOperation_resourceExists,
						null, message, MessageDialog.QUESTION, 0, labels) {
					@Override
					protected int getShellStyle() {
						return super.getShellStyle() | SWT.SHEET;
					}
				};
				dialog.open();
				if (dialog.getReturnCode() == SWT.DEFAULT) {
					result[0] = IDialogConstants.CANCEL_ID;
				} else {
					result[0] = resultId[dialog.getReturnCode()];
