			for (Repository repo : org.eclipse.egit.core.Activator.getDefault()
					.getRepositoryCache().getAllRepositories()) {
				if (!dirs.contains(repo.getDirectory())) {
					listenToRepository(repo);
					dirs.add(repo.getDirectory());
				}
			}
