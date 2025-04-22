	private ContainerNode getOrCreateContainerNode(IPath containerPath, Type fileType) {
		ContainerNode containerNode = containerNodes.get(containerPath);
		if (containerNode != null) {
			return containerNode;
		} else {
			Repository repository = getRepository();
			IContainer resource = repository == null ? null : ResourceUtil
					.getContainerForLocation(repository,
							containerPath.toString());
			ContainerNode node = new ContainerNode(containerPath, resource);
			node.setOnlyEqualContent(fileType == Type.FILE_BOTH_SIDES_SAME);
			containerNodes.put(containerPath, node);
			if (containerPath.segmentCount() > 0) {
				IPath parentPath = containerPath.removeLastSegments(1);
				ContainerNode parentNode = getOrCreateContainerNode(parentPath,
						fileType);
				parentNode.addChild(node);
			}
			return node;
		}
	}

