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
