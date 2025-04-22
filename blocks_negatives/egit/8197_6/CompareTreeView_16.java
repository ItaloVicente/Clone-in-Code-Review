			PathNode parentNode = (PathNode) parentElement;
			IPath parent = parentNode.path;
			List<PathNode> children = new ArrayList<PathNode>();
			for (IPath childPath : baseVersionPathsWithChildren) {
				if (childPath.segmentCount() > 0
						&& childPath.removeLastSegments(1).equals(parent)) {
					children.add(new PathNode(childPath, Type.FOLDER));
				}
			}
			for (IPath mapPath : baseVersionMap.keySet()) {
				if (mapPath.removeLastSegments(1).equals(parent)
						&& (showEquals || !equalContentPaths.contains(mapPath))) {
					if (addedPaths.contains(mapPath))
						children.add(new PathNode(mapPath, Type.FILE_ADDED));
					else if (equalContentPaths.contains(mapPath))
						children.add(new PathNode(mapPath,
								Type.FILE_BOTH_SIDES_SAME));
					else
						children.add(new PathNode(mapPath,
								Type.FILE_BOTH_SIDES_DIFFER));
				}
			}
			if (parentNode.type == Type.FOLDER) {
				List<PathNodeAdapter> deletedChildren = compareVersionPathsWithChildren
						.get(parent);
				if (deletedChildren != null)
					for (PathNodeAdapter path : deletedChildren)
						children.add(path.pathNode);

			}
			return children.toArray();
