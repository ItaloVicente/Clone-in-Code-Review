			IPath parent = ((PathNode) element).path;
			for (IPath childPath : baseVersionPathsWithChildren) {
				if (childPath.removeLastSegments(1).equals(parent))
					return true;
			}
			for (IPath mapPath : baseVersionMap.keySet()) {
				if (mapPath.removeLastSegments(1).equals(parent)
						&& (showEquals || !equalContentPaths.contains(mapPath))) {
					return true;
				}
			}
			return false;
