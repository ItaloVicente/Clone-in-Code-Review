		if (includedResources.isEmpty()) {
			final IProject[] workspaceProjects = ROOT.getProjects();
			for (IProject project : workspaceProjects) {
				RepositoryMapping mapping = RepositoryMapping
						.getMapping(project);
				if (mapping != null && mapping.getRepository() == repo)
					projects.add(project);
			}
		} else {
			for (IResource res : includedResources) {
				IProject project = res.getProject();
				RepositoryMapping mapping = RepositoryMapping
						.getMapping(project);
				if (mapping != null && mapping.getRepository() == repo)
					projects.add(project);
			}
