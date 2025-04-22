								Shell shell = PlatformUI.getWorkbench()
										.getActiveWorkbenchWindow().getShell();
								MessageDialog
										.openInformation(
												shell,
												UIText.MergeAction_MergeCanceledTitle,
												UIText.MergeAction_MergeCanceledMessage);
