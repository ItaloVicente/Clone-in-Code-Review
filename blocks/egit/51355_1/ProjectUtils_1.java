							IPath absolutePath = mapping
									.getGitDirAbsolutePath();
							if (absolutePath != null) {
								projectsToConnect.put(project,
										absolutePath.toFile());
							}
