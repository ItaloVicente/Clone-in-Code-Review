						} else if (missingSubmodules.remove(subRepoPath)) {
							File gitDir = new File(
									new File(repository.getDirectory()
											Constants.MODULES)
									subRepoPath);
							if (!gitDir.isDirectory()) {
								File dir = SubmoduleWalk.getSubmoduleDirectory(
										repository
								if (dir.isDirectory() && !hasFiles(dir)) {
									missing.remove(subRepoPath);
								}
