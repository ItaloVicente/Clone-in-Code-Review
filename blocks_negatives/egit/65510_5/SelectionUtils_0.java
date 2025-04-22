		for (IPath location : locations) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(location);
			Repository repo;
			if (mapping != null) {
				repo = mapping.getRepository();
			} else {
				repo = org.eclipse.egit.core.Activator.getDefault()
						.getRepositoryCache().getRepository(location);
