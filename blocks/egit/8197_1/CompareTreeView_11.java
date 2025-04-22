			if (element instanceof PathNode) {
				PathNode pathNode = (PathNode) element;
				IPath path = pathNode.getPath();
				if (path.segmentCount() == 0)
					return null;
				else
					return containerNodes.get(path.removeLastSegments(1));
			} else
