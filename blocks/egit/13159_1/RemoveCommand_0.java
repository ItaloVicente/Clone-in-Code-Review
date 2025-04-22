	private static void closeSubmoduleRepositories(Repository repo)
			throws IOException {
		SubmoduleWalk walk = SubmoduleWalk.forIndex(repo);
		try {
			while (walk.next()) {
				Repository subRepo = walk.getRepository();
				if (subRepo != null) {
					RepositoryCache cache = null;
					try {
						cache = org.eclipse.egit.core.Activator.getDefault()
								.getRepositoryCache();
					} finally {
						if (cache != null)
							cache.lookupRepository(subRepo.getDirectory())
									.close();
						subRepo.close();
					}
				}
			}
		} finally {
			walk.release();
		}
	}

