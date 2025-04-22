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
