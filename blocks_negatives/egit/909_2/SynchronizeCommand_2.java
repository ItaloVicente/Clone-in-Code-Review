				Set<IProject> repoProjects = new HashSet<IProject>();
				final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
				for (IProject project : projects) {
					RepositoryMapping mapping = RepositoryMapping.getMapping(project);
					if (mapping != null && mapping.getRepository() == repo) {
						repoProjects.add(project);
					}
				}

