	private Set<IResource> getConflictingFilesFrom(IContainer container)
			throws IOException {
		final Set<IResource> conflictingResources = new LinkedHashSet<IResource>();
		final RepositoryMapping mapping = RepositoryMapping
				.getMapping(container);
		if (mapping == null) {
			return conflictingResources;
		}
		final IndexDiffCacheEntry indexDiffCacheEntry = org.eclipse.egit.core.Activator
				.getDefault().getIndexDiffCache()
				.getIndexDiffCacheEntry(mapping.getRepository());
		if (indexDiffCacheEntry == null) {
			return conflictingResources;
		}
		final IndexDiffData indexDiffData = indexDiffCacheEntry.getIndexDiff();
		if (indexDiffData != null) {
			final IPath containerPath = container.getLocation();
			final File workTree = mapping.getWorkTree();
			if (workTree != null) {
				final IPath workDirPrefix = new Path(
						workTree.getCanonicalPath());
				for (String conflicting : indexDiffData.getConflicting()) {
					final IPath resourcePath = workDirPrefix
							.append(conflicting);
					if (containerPath.isPrefixOf(resourcePath)) {
						final IPath containerRelativePath = resourcePath
								.removeFirstSegments(containerPath
										.segmentCount());
						conflictingResources.add(container
								.getFile(containerRelativePath));
					}
				}
