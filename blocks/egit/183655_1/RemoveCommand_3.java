					IFile agnosticSymLink = null;
					if (projectsToDelete.get(0)
							.getFile(Constants.DOT_GIT)
							.exists()) {
						agnosticSymLink = projectsToDelete.get(0)
								.getFile(Constants.DOT_GIT);
					}
