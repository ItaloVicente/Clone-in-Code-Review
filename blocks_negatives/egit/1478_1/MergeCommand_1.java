							try {
								MessageDialog.openInformation(
										getActiveShell(event),
										UIText.MergeAction_MergeResultTitle, op
												.getResult().toString());
							} catch (ExecutionException e) {
								Activator.handleError(
										op.getResult().toString(), null, true);
							}
