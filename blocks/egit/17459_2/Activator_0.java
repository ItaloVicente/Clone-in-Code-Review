						if (project.isAccessible()) {
							if (RepositoryProvider.getProvider(project) instanceof GitProvider) {
								IResource dotGit = project
										.findMember(Constants.DOT_GIT);
								if (dotGit != null
										&& dotGit.getType() == IResource.FOLDER)
									GitProjectData.reconfigureWindowCache();
							}
						} else {
							IPath locationPath = project.getLocation();
							if (locationPath != null) {
								File locationDir = locationPath.toFile();
								File dotGit = new File(locationDir,
										Constants.DOT_GIT);
								if (dotGit.exists() && dotGit.isDirectory()) {
									GitProjectData.reconfigureWindowCache();
								}
							}
