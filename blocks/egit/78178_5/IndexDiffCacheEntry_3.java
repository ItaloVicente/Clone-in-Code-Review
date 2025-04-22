				if (event.getType() == IResourceChangeEvent.PRE_DELETE) {
					IResource resource = event.getResource();
					if (resource.getType() == IResource.PROJECT) {
						IPath projectPath = resource.getLocation();
						if (projectPath != null) {
							IPath repoPath = ResourceUtil
									.getRepositoryRelativePath(projectPath,
											repository);
							if (repoPath != null) {
								deletedProjects.put((IProject) resource,
										projectPath);
							}
						}
					}
					return;
				}
				GitResourceDeltaVisitor visitor = new GitResourceDeltaVisitor(
						repository, deletedProjects);
