		Runnable query = new Runnable() {
			@Override
			public void run() {
				String message;
				int resultId[] = { IDialogConstants.YES_ID,
						IDialogConstants.YES_TO_ALL_ID, IDialogConstants.NO_ID,
						IDialogConstants.CANCEL_ID };
				String labels[] = new String[] { IDialogConstants.YES_LABEL,
						IDialogConstants.YES_TO_ALL_LABEL,
						IDialogConstants.NO_LABEL,
						IDialogConstants.CANCEL_LABEL };

				if (destination.getType() == IResource.FOLDER) {
					if (homogenousResources(source, destination)) {
						message = NLS
								.bind(
										IDEWorkbenchMessages.CopyFilesAndFoldersOperation_overwriteMergeQuestion,
										destination.getFullPath()
												.makeRelative());
					} else {
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
					}
				} else {
					String[] bindings = new String[] {
							IDEResourceInfoUtils.getLocationText(destination),
							IDEResourceInfoUtils
									.getDateStringValue(destination),
							IDEResourceInfoUtils.getLocationText(source),
							IDEResourceInfoUtils.getDateStringValue(source) };
					message = NLS
							.bind(
									IDEWorkbenchMessages.CopyFilesAndFoldersOperation_overwriteWithDetailsQuestion,
									bindings);
				}
				MessageDialog dialog = new MessageDialog(
						messageShell,
						IDEWorkbenchMessages.CopyFilesAndFoldersOperation_resourceExists,
						null, message, MessageDialog.QUESTION, 0, labels) {
					@Override
					protected int getShellStyle() {
						return super.getShellStyle() | SWT.SHEET;
					}
				};
				dialog.open();
				if (dialog.getReturnCode() == SWT.DEFAULT) {
					result[0] = IDialogConstants.CANCEL_ID;
				} else {
					result[0] = resultId[dialog.getReturnCode()];
				}
