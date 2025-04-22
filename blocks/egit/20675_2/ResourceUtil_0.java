
	public static void saveLocalHistory(Repository repository) {
		IndexDiffCacheEntry indexDiffCacheEntry = org.eclipse.egit.core.Activator
				.getDefault().getIndexDiffCache()
				.getIndexDiffCacheEntry(repository);
		IndexDiffData indexDiffData = indexDiffCacheEntry.getIndexDiff();
		Collection<IResource> changedResources = indexDiffData
				.getChangedResources();
		if (changedResources != null) {
			for (IResource changedResource : changedResources) {
				if (changedResource instanceof IFile
						&& changedResource.exists()) {
					try {
						ResourceUtil.saveLocalHistory(changedResource);
					} catch (CoreException e) {
					}
				}
			}
		}
	}

	private static void saveLocalHistory(IResource resource)
			throws CoreException {
		if (!resource.isSynchronized(IResource.DEPTH_ZERO))
			resource.refreshLocal(IResource.DEPTH_ZERO, null);
		((IFile) resource).appendContents(
				new ByteArrayInputStream(new byte[0]), IResource.KEEP_HISTORY,
				null);
	}
