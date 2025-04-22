		return getExistingResourceWithShortestPath(files);
	}

	private static <T extends IResource> T getExistingResourceWithShortestPath(
			T[] resources) {
		int shortestPathSegmentCount = Integer.MAX_VALUE;
		T shortestPath = null;
		for (T resource : resources) {
			if (!resource.exists())
				continue;
			IPath fullPath = resource.getFullPath();
			int segmentCount = fullPath.segmentCount();
			if (segmentCount < shortestPathSegmentCount) {
				shortestPath = resource;
				shortestPathSegmentCount = segmentCount;
			}
		}
		return shortestPath;
