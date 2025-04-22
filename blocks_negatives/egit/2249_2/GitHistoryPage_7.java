	private ArrayList<String> buildFilterPaths(final IResource[] inResources,
			final File[] inFiles, final Repository db)
			throws IllegalStateException {
		final ArrayList<String> paths;
		if (inResources != null) {
			paths = new ArrayList<String>(inResources.length);
			for (final IResource r : inResources) {
				final RepositoryMapping map = RepositoryMapping.getMapping(r);
				if (map == null)
					continue;
				if (db != map.getRepository()) {
					throw new IllegalStateException(
							UIText.AbstractHistoryCommanndHandler_NoUniqueRepository);
				}

				if (showAllFilter == ShowFilter.SHOWALLFOLDER) {
					final String path;
					if (r.getParent() instanceof IWorkspaceRoot)
						path = map.getRepoRelativePath(r.getProject());
					else
						path = map.getRepoRelativePath(r.getParent());
					if (path != null && path.length() > 0)
						paths.add(path);
				} else if (showAllFilter == ShowFilter.SHOWALLPROJECT) {
					final String path = map.getRepoRelativePath(r.getProject());
					if (path != null && path.length() > 0)
						paths.add(path);
				} else if (showAllFilter == ShowFilter.SHOWALLREPO) {
				} else /* if (showAllFilter == ShowFilter.SHOWALLRESOURCE) */{
					final String path = map.getRepoRelativePath(r);
					if (path != null && path.length() > 0)
						paths.add(path);
				}
