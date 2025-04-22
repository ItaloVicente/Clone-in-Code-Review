						if (destination.isLinked()) {
							message = NLS
									.bind(
											IDEWorkbenchMessages.CopyFilesAndFoldersOperation_overwriteNoMergeLinkQuestion,
											destination.getFullPath()
													.makeRelative());
						} else {
							message = NLS
									.bind(
											IDEWorkbenchMessages.CopyFilesAndFoldersOperation_overwriteNoMergeNoLinkQuestion,
											destination.getFullPath()
													.makeRelative());
						}
						resultId = new int[] { IDialogConstants.YES_ID,
								IDialogConstants.NO_ID,
								IDialogConstants.CANCEL_ID };
						labels = new String[] { IDialogConstants.YES_LABEL,
								IDialogConstants.NO_LABEL,
								IDialogConstants.CANCEL_LABEL };
