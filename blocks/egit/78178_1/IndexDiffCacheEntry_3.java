				if (event.getType() == IResourceChangeEvent.PRE_DELETE) {
					IResource project = event.getResource();
					IPath projectPath = project.getLocation();
					if (projectPath != null) {
						IPath repoPath = ResourceUtil.getRepositoryRelativePath(
								projectPath, repository);
						if (repoPath != null) {
							deletedProjects.put(project, projectPath);
						}
					}
					return;
				}
				GitResourceDeltaVisitor visitor = new GitResourceDeltaVisitor(
						repository, deletedProjects);
