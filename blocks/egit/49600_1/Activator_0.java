			Set<Repository> repos;
			synchronized (repositoriesChanged) {
				if (repositoriesChanged.isEmpty()) {
					return Status.OK_STATUS;
				}
				repos = new LinkedHashSet<>(repositoriesChanged);
				repositoriesChanged.clear();
			}
			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
					.getProjects();
			Set<IProject> toRefresh = new LinkedHashSet<>();
			for (IProject p : projects) {
				if (!p.isAccessible()) {
					continue;
				}
				RepositoryMapping mapping = RepositoryMapping.getMapping(p);
				if (mapping != null
						&& repos.contains(mapping.getRepository())) {
					toRefresh.add(p);
				}
			}
			monitor.beginTask(UIText.Activator_refreshingProjects,
					toRefresh.size());
