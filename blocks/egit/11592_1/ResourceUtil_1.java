		int shortestContainerPath = Integer.MAX_VALUE;
		IContainer containeeWithShortestPath = null;
		for (IContainer container : containers) {
			if (!container.exists())
				continue;
			IPath fullPath = container.getFullPath();
			if (fullPath.segmentCount() < shortestContainerPath) {
				shortestContainerPath = fullPath.segmentCount();
				containeeWithShortestPath = container;
			}
		}
		return containeeWithShortestPath;
