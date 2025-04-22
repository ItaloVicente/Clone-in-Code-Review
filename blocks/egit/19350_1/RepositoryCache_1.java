	public Repository getRepository(final IResource resource) {
		Repository[] repositories = org.eclipse.egit.core.Activator
				.getDefault().getRepositoryCache().getAllRepositories();
		Repository repository = null;
		int largestSegmentCount = 0;
		for (Repository r : repositories) {
			if (!r.isBare()) {
				try {
					IPath repoPath = new Path(r.getWorkTree()
							.getCanonicalPath());
					IPath location = resource.getLocation();
					if (location != null && repoPath.isPrefixOf(location)) {
						if (repository == null
								|| repoPath.segmentCount() > largestSegmentCount) {
							repository = r;
							largestSegmentCount = repoPath.segmentCount();
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

