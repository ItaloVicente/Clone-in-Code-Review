				IPath containerPath = currentPath.removeLastSegments(1);
				ContainerNode containerNode = getOrCreateContainerNode(
						containerPath, type);

				FileNode fileNode = new FileNode(currentPath, file, type, left,
						right);
				containerNode.addChild(fileNode);
				fileNodes.put(currentPath, fileNode);

				if (type != Type.FILE_BOTH_SIDES_SAME) {
					IPath path = currentPath;
					while (path.segmentCount() > 0) {
						path = path.removeLastSegments(1);
						ContainerNode node = containerNodes.get(path);
						node.setOnlyEqualContent(false);
