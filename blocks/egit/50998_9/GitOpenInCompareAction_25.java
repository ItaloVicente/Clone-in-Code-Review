	private Repository getRepository(GitModelBlob blob) {
		IPath location = blob.getLocation();
		RepositoryMapping mapping = RepositoryMapping.getMapping(location);
		if (mapping == null) {
			return null;
		}
		return mapping.getRepository();
	}

