				if (type != Type.FILE_BOTH_SIDES_SAME) {
					IPath path = currentPath;
					while (path.segmentCount() > 0) {
						path = path.removeLastSegments(1);
						ContainerNode node = containerNodes.get(path);
						node.setOnlyEqualContent(false);
					}
