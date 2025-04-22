			parentShell.getDisplay().syncExec(() -> MessageDialog
					.openError(
							parentShell,
							IDEWorkbenchMessages.CopyProjectOperation_copyFailedTitle,
							NLS
									.bind(
											IDEWorkbenchMessages.CopyProjectOperation_internalError,
											message)));
