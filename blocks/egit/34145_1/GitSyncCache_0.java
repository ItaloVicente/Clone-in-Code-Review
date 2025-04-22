	private static Collection<String> getForceInCachePaths(GitSynchronizeData gsd) {
		final Set<String> forceInCache = new HashSet<String>(gsd
				.getIncludedResources().size());
		final Path repositoryPath = new Path(gsd.getRepository().getWorkTree()
				.getAbsolutePath());
		for (IResource resource : gsd.getIncludedResources()) {
			IPath resourceLocation = resource.getLocation();
			forceInCache.add(resourceLocation.makeRelativeTo(repositoryPath)
					.toString());
		}
		return forceInCache;
	}

