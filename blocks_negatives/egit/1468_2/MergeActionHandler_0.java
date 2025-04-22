								try {
									MessageDialog
											.openInformation(
													getShell(event),
													UIText.MergeAction_MergeCanceledTitle,
													UIText.MergeAction_MergeCanceledMessage);
								} catch (ExecutionException e) {
									Activator
											.handleError(
													UIText.MergeAction_MergeCanceledMessage,
													null, true);
								}
