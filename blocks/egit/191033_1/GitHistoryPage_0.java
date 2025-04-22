				IPath pathToAdd;
				if (!file.isAbsolute()) {
					pathToAdd = filePath;
				} else {
					if (gitDirPath.isPrefixOf(filePath)) {
						throw new IllegalStateException(NLS.bind(
								UIText.GitHistoryPage_FileOrFolderPartOfGitDirMessage,
								filePath.toOSString()));
					}
					pathToAdd = filePath.removeFirstSegments(segmentCount);
