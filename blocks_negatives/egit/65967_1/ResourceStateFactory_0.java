		Repository repository = null;
		RepositoryMapping mapping = RepositoryMapping.getMapping(path);
		if (mapping != null) {
			repository = mapping.getRepository();
		} else {
			repository = Activator.getDefault().getRepositoryCache()
					.getRepository(path);
		}
