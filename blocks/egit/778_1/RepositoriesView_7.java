	private void showResource(final IResource resource) {
		try {
			IProject project = resource.getProject();
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			if (mapping == null)
				return;
