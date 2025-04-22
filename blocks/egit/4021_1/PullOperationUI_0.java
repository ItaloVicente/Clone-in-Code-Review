				} else if (status.getException() instanceof TransportException) {
					MessageDialog
							.openWarning(
									shell,
									UIText.PullOperationUI_PullFailed,
									NLS.bind(
											UIText.PullOperationUI_GitDataTransferFailed,
											status.getMessage()));
