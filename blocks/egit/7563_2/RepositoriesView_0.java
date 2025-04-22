	private void showResources(final List<IResource> resources) {
		final List<RepositoryTreeNode> nodesToShow = new ArrayList<RepositoryTreeNode>();

		IResource[] r = resources.toArray(new IResource[resources.size()]);
		Map<Repository, Collection<String>> resourcesByRepo = ResourceUtil.splitResourcesByRepository(r);
		for (Map.Entry<Repository, Collection<String>> entry : resourcesByRepo.entrySet()) {
			Repository repository = entry.getKey();
			try {
				boolean added = repositoryUtil.addConfiguredRepository(repository.getDirectory());
				if (added)
					scheduleRefresh(0);
			} catch (IllegalArgumentException iae) {
				Activator.handleError(iae.getMessage(), iae, false);
				continue;
			}
