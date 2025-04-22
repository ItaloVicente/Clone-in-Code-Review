			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			Set<IProject> toRefresh= new HashSet<IProject>();
			for (IProject p : projects) {
				RepositoryMapping mapping = RepositoryMapping.getMapping(p);
				if (mapping != null && mapping.getRepository() == e.getRepository()) {
					toRefresh.add(p);
