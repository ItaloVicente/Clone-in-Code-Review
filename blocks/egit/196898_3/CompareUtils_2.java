				String rightPath = rightGitPath;
				if (rightGitPath == null) {
					rightPath = findRename(repository, leftRev, rightRev,
							leftGitPath, monitor);
					if (monitor.isCanceled()) {
						return Status.CANCEL_STATUS;
					}
				}

