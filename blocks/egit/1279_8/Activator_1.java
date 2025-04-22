		private void mayTriggerRefresh(RepositoryEvent e) {
			repositoriesChanged.add(e.getRepository());
			if (!Activator.getDefault().getPreferenceStore()
					.getBoolean(UIPreferences.REFESH_ONLY_WHEN_ACTIVE)
					|| isActive())
				triggerRefresh();
		}

		void triggerRefresh() {
			if (GitTraceLocation.UI.isActive())
				GitTraceLocation.getTrace().trace(
						GitTraceLocation.UI.getLocation(), "Triggered refresh"); //$NON-NLS-1$
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
