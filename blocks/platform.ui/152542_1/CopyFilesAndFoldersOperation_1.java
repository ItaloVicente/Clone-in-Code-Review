					if (workspace.getRoot().exists(prefix.append(string))) {
						return IDEWorkbenchMessages.CopyFilesAndFoldersOperation_nameExists;
					}
					return null;
				}
			};
