
			SubmoduleWalk walk = SubmoduleWalk.forIndex(repo);
			while (walk.next()) {
				Repository subRepo = walk.getRepository();
				if (subRepo != null) {
					final RepositoryCache cache = org.eclipse.egit.core.Activator
							.getDefault().getRepositoryCache();
					cache.lookupRepository(subRepo.getDirectory()).close();
					subRepo.close();
				}
			}
			walk.release();

