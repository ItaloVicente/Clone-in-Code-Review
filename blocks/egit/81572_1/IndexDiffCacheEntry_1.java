					IPath projectPath = resource.getLocation();
					if (projectPath != null) {
						IPath repoPath = ResourceUtil.getRepositoryRelativePath(
								projectPath, repository);
						if (repoPath != null) {
							deletedProjects.put((IProject) resource,
									projectPath);
