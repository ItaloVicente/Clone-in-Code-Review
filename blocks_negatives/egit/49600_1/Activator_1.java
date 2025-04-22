			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
					.getProjects();
			Set<IProject> toRefresh = new HashSet<IProject>();
			synchronized (repositoriesChanged) {
				for (IProject p : projects) {
					RepositoryMapping mapping = RepositoryMapping.getMapping(p);
					if (mapping != null
							&& repositoriesChanged.contains(mapping
									.getRepository())) {
						toRefresh.add(p);
					}
				}
				repositoriesChanged.clear();
			}
			synchronized (projectsToScan) {
				projectsToScan.addAll(toRefresh);
