	public Repository getRepository(final IResource resource) {
		Repository[] repositories = org.eclipse.egit.core.Activator
				.getDefault().getRepositoryCache().getAllRepositories();
		Repository repository = null;
		IPath repositoryPath = null;
		for (Repository r : repositories) {
			if (!r.isBare()) {
				try {
					IPath repoPath = new Path(r.getWorkTree()
							.getCanonicalPath());
					if (repoPath.isPrefixOf(resource.getLocation())) {
						if (repository == null || repositoryPath == null
								|| repositoryPath.isPrefixOf(repoPath)) {
							repository = r;
							repositoryPath = repoPath;
						}
					}
				} catch (IOException e) {
					Activator
							.error("looking up working tree path of git repository failed", e); //$NON-NLS-1$
				}
			}
		}
		return repository;
	}

