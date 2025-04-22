								try {
									MessageDialog
											.openInformation(
													getShell(event),
													UIText.MergeAction_MergeResultTitle,
													op.getResult().toString());
								} catch (ExecutionException e) {
									Activator.handleError(op.getResult()
											.toString(), null, true);
								}
