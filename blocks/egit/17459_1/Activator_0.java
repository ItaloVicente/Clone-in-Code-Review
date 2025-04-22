						if (project.isAccessible()) {
							if (RepositoryProvider.getProvider(project) instanceof GitProvider) {
								IResource dotGit = project
										.findMember(Constants.DOT_GIT);
								if (dotGit != null
										&& dotGit.getType() == IResource.FOLDER)
									GitProjectData.reconfigureWindowCache();
							}
						} else {
							File location = project.getLocation().toFile();
							File dotGit = new File(location, Constants.DOT_GIT);
							if (dotGit.exists() && dotGit.isDirectory()) {
