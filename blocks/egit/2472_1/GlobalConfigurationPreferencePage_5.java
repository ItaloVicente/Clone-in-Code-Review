		if (repositories == null) {
			repositories = new ArrayList<Repository>();
			List<String> repoPaths = Activator.getDefault().getRepositoryUtil().getConfiguredRepositories();
			RepositoryCache repositoryCache = org.eclipse.egit.core.Activator.getDefault().getRepositoryCache();
			for (String repoPath : repoPaths) {
				try {
					Repository repository = repositoryCache.lookupRepository(new File(repoPath));
					repositories.add(repository);
				} catch (IOException e) {
					continue;
				}
			}
		}
