			RepositoryMapping mapping = RepositoryMapping.getMapping(location);
			Repository repo;
			if (mapping != null) {
				repo = mapping.getRepository();
			} else {
				repo = org.eclipse.egit.core.Activator.getDefault()
						.getRepositoryCache().getRepository(location);
			}
			if (repo == null) {
				hadNull = true;
			}
			if (result == null) {
				result = repo;
			}
			boolean mismatch = hadNull && result != null;
			if (mismatch || result != repo) {
				if (warn) {
