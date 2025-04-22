				repos = new LinkedHashSet<>(repositoriesChanged);
				repositoriesChanged.clear();
			}
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IProject[] projects = workspace.getRoot().getProjects();
			final Set<IProject> toRefresh = new LinkedHashSet<>();
			for (IProject p : projects) {
				if (!p.isAccessible()) {
					continue;
