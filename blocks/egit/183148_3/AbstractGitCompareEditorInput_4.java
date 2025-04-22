		if (child != root && !repository.isBare()) {
			IContainer container = file != null ? file.getParent() : null;
			path = location.removeLastSegments(1);
			IDiffContainer folder = child;
			while (folder != root) {
				if (folder instanceof FolderNode) {
					if (container != null) {
						((FolderNode) folder).setContainer(container);
						if (container.isLinked()) {
							container = null;
						} else {
							container = container.getParent();
							if (container.getType() == IResource.ROOT) {
								container = null;
							}
						}
					} else if (path != null) {
						((FolderNode) folder).setPath(path);
					}
					if (path != null && pathLength > 0) {
						path = path.removeLastSegments(1);
						pathLength--;
					} else {
						break;
					}
				}
				folder = folder.getParent();
			}
		}
