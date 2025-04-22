				final String returnCode[] = { CANCEL };
				final String msg = NLS.bind(IDEWorkbenchMessages.CopyFilesAndFoldersOperation_overwriteQuestion,
						pathString);
				final String[] options = { IDEWorkbenchMessages.CopyFilesAndFoldersOperation_overwriteButtonLabel,
						IDEWorkbenchMessages.CopyFilesAndFoldersOperation_overwriteAllButtonLabel,
						IDEWorkbenchMessages.CopyFilesAndFoldersOperation_dontOverwriteButtonLabel,
						IDialogConstants.CANCEL_LABEL };
				messageShell.getDisplay().syncExec(() -> {
					MessageDialog dialog = new MessageDialog(messageShell,
							IDEWorkbenchMessages.CopyFilesAndFoldersOperation_question, null, msg,
							MessageDialog.QUESTION, 0, options) {
						@Override
						protected int getShellStyle() {
							return super.getShellStyle() | SWT.SHEET;
						}
					};
					dialog.open();
					int returnVal = dialog.getReturnCode();
					String[] returnCodes = { YES, ALL, NO, CANCEL };
					returnCode[0] = returnVal == -1 ? CANCEL : returnCodes[returnVal];
				});
				if (returnCode[0] == ALL) {
					alwaysOverwrite = true;
				} else if (returnCode[0] == CANCEL) {
					canceled = true;
				}
				return returnCode[0];
