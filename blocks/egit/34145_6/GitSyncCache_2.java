	private static Collection<String> getPathsToCache(GitSynchronizeData gsd) {
		Set<IResource> resourcesToInclude = gsd.getIncludedResources();
		final Set<String> pathsToCache;
		if (resourcesToInclude != null && !resourcesToInclude.isEmpty()) {
			pathsToCache = new HashSet<String>(resourcesToInclude.size());
			final Path repositoryPath = new Path(gsd.getRepository()
					.getWorkTree().getAbsolutePath());
			for (IResource resource : gsd.getIncludedResources()) {
				IPath resourceLocation = resource.getLocation();
				if (resourceLocation != null) {
					pathsToCache.add(resourceLocation.makeRelativeTo(
							repositoryPath).toString());
				}
			}
		} else {
			pathsToCache = Collections.emptySet();
		}
		return pathsToCache;
	}

