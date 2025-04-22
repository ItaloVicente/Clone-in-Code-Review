				IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
						.getProjects();
				for (IProject project : projects) {
					RepositoryMapping mapping = RepositoryMapping
							.getMapping(project);
					if (mapping != null && mapping.getRepository().equals(repo)) {
						try {
							project
									.refreshLocal(IResource.DEPTH_INFINITE,
											null);
						} catch (CoreException e) {
							Activator.handleError(e.getMessage(), e, false);
						}
					}
