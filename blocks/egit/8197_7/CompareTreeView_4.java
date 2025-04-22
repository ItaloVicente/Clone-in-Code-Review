
				IFileRevision left = null;
				IFileRevision right = null;
				String repoRelativePath = baseVersionIterator != null
						? baseVersionIterator.getEntryPathString()
						: compareVersionIterator.getEntryPathString();
				IPath currentPath = new Path(repoRelativePath);

				monitor.setTaskName(currentPath.toString());

				IFile file = ResourceUtil.getFileForLocation(repository,
						repoRelativePath);
				if (baseVersionIterator != null) {
					if (baseCommit == null) {
						if (file != null)
							left = new LocalFileRevision(file);
						else {
							IPath path = getRepositoryPath().append(
									repoRelativePath);
							left = new WorkingTreeFileRevision(
									path.toFile());
						}
					} else {
						left = GitFileRevision
								.inCommit(repository, baseCommit,
										repoRelativePath,
										tw.getObjectId(baseTreeIndex));
					}
				}

				if (compareVersionIterator != null) {
