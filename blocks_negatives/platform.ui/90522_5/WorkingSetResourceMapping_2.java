		ResourceMapping[] mappings = getMappings();
		for (int i = 0; i < mappings.length; i++) {
			ResourceMapping mapping = mappings[i];
			IProject[] projects = mapping.getProjects();
			for (int j = 0; j < projects.length; j++) {
				IProject project = projects[j];
