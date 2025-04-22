				return false;
			IPath parent = ((PathNode) element).path;
			for (IPath childPath : rightPathsWithChildren) {
				if (childPath.removeLastSegments(1).equals(parent)) {
					return true;
				}
			}
			for (IPath mapPath : rightVersionMap.keySet()) {
				if (mapPath.removeLastSegments(1).equals(parent)
						&& (!showDeletedOnly || rightOnly.contains(mapPath))
						&& (showEquals || !equalIds.contains(mapPath))) {
					return true;
				}
			}
			return false;
