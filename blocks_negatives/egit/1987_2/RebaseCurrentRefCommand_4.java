					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							Shell shell = PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell();
							if (abortedDueToConflict.get())
								MessageDialog
										.openError(
												shell,
												UIText.RebaseCurrentRefCommand_AbortedDialogTitle,
												UIText.RebaseCurrentRefCommand_AbortedDialogMessage);
							else
								new RebaseResultDialog(shell, repository,
										rebase.getResult()).open();
						}
					});
