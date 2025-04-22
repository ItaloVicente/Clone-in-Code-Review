				if (gitDirPath.isPrefixOf(filePath)) {
					setErrorMessage(NLS
							.bind(
									UIText.GitHistoryPage_FileOrFolderPartOfGitDirMessage,
									filePath.toOSString()));
					return false;
				}

				IPath pathToAdd = filePath.removeFirstSegments(segmentCount)
						.setDevice(null);
				if (!pathToAdd.isEmpty()) {
					paths.add(pathToAdd.toString());
				}
