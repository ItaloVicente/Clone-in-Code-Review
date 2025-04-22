	private Set<IResource> getConflictingFilesFrom(IContainer container)
			throws IOException {
		final Set<IResource> conflictingResources = new LinkedHashSet<IResource>();
		final RepositoryMapping mapping = RepositoryMapping
				.getMapping(container);
		final IndexDiffData indexDiffData = org.eclipse.egit.core.Activator
				.getDefault().getIndexDiffCache()
				.getIndexDiffCacheEntry(mapping.getRepository()).getIndexDiff();
		if (indexDiffData != null) {
			final IPath containerPath = container.getLocation();
			final IPath workDirPrefix = new Path(mapping.getWorkTree()
					.getCanonicalPath());
			for (String conflicting : indexDiffData.getConflicting()) {
				final IPath resourcePath = workDirPrefix.append(conflicting);
				if (containerPath.isPrefixOf(resourcePath)) {
					final IPath containerRelativePath = resourcePath
							.removeFirstSegments(containerPath.segmentCount());
					conflictingResources.add(container
							.getFile(containerRelativePath));
				}
			}
		}
		return conflictingResources;
	}

