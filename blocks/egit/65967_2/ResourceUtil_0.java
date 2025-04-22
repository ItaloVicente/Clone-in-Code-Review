
	@Nullable
	public static Repository getRepository(@NonNull IResource resource) {
		RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
		if (mapping != null) {
			return mapping.getRepository();
		}
		return Activator.getDefault().getRepositoryCache()
				.getRepository(resource);
	}

	@Nullable
	public static Repository getRepository(@NonNull IPath path) {
		RepositoryMapping mapping = RepositoryMapping.getMapping(path);
		if (mapping != null) {
			return mapping.getRepository();
		}
		return Activator.getDefault().getRepositoryCache().getRepository(path);
	}

