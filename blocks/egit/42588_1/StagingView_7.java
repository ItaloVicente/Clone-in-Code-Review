	private static Repository getRepositoryOrNestedSubmoduleRepository(
			IResource resource) {
		IProject project = resource.getProject();
		RepositoryMapping mapping = RepositoryMapping.getMapping(project);
		if (mapping == null) {
			return null;
		}
		Repository repo = mapping.getSubmoduleRepository(resource);
		if (repo == null) {
			repo = mapping.getRepository();
