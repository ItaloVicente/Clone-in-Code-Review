	private ISchedulingRule calculateSchedulingRule() {
		Set<IContainer> containers = new HashSet<IContainer>();
		for (IPath path : paths) {
			IResource resource = ResourceUtil.getResourceForLocation(path);
			if (resource != null) {
				containers.add(resource.getParent());
			}
		}
		return new MultiRule(containers.toArray(new IResource[containers.size()]));
	}

	private void refreshIndexDiffCache(List<IPath> refreshCachePaths, boolean refreshAll) {
		Map<Repository, Collection<String>> resourcesByRepository = ResourceUtil.splitPathsByRepository(refreshCachePaths);
