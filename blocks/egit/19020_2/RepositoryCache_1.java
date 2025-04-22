	public Repository getRepository(final IResource resource) {
		Repository[] repositories = org.eclipse.egit.core.Activator
				.getDefault().getRepositoryCache().getAllRepositories();
		Repository repository = null;
		int segmentCount = 0;
		for (Repository r : repositories) {
			if (!r.isBare()) {
				try {
					IPath repoPath = new Path(r.getWorkTree()
							.getCanonicalPath());
					IPath location = resource.getLocation();
					if (location != null
							&& repoPath.isPrefixOf(resource.getLocation())) {
						if (repository == null
								|| repoPath.segmentCount() > segmentCount) {
							repository = r;
							segmentCount = repoPath.segmentCount();
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

