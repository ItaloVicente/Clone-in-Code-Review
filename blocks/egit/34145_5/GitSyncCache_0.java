	private static Collection<String> getForceInCachePaths(GitSynchronizeData gsd) {
		Set<IResource> resourceToIncluse = gsd.getIncludedResources();
		final Set<String> forceInCache;
		if (resourceToIncluse != null && !resourceToIncluse.isEmpty()) {
			forceInCache = new HashSet<String>(resourceToIncluse.size());
			final Path repositoryPath = new Path(gsd.getRepository()
					.getWorkTree().getAbsolutePath());
			for (IResource resource : gsd.getIncludedResources()) {
				IPath resourceLocation = resource.getLocation();
				if (resourceLocation != null) {
					forceInCache.add(resourceLocation.makeRelativeTo(
							repositoryPath).toString());
				}
			}
		} else {
			forceInCache = Collections.emptySet();
		}
		return forceInCache;
	}

