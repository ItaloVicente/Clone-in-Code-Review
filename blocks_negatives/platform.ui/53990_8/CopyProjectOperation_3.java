			parentShell.getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					MessageDialog
							.openError(
									parentShell,
									IDEWorkbenchMessages.CopyProjectOperation_copyFailedTitle,
									NLS
											.bind(
													IDEWorkbenchMessages.CopyProjectOperation_internalError,
													message));
				}
			});
