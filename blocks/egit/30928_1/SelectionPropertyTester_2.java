	private static Repository getRepositoryOfMapping(IResource resource) {
		RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
		if (mapping != null)
			return mapping.getRepository();
		return null;
	}
