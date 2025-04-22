
				if (gitDirPath.isPrefixOf(filePath)) {
					setErrorMessage(NLS
							.bind(
									UIText.GitHistoryPage_FileOrFolderPartOfGitDirMessage,
									filePath.toOSString()));
					return false;
				}

