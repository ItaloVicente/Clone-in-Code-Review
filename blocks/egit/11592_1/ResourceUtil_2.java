		int shortestPathSegmentCount = Integer.MAX_VALUE;
		IFile shortestPath = null;
		for (IFile file : files) {
			IPath fullPath = file.getFullPath();
			if (!file.exists())
				continue;
			if (fullPath.segmentCount() < shortestPathSegmentCount) {
				shortestPath = file;
				shortestPathSegmentCount = fullPath.segmentCount();
			}
		}
		return shortestPath;
