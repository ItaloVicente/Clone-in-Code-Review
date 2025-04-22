		if (child != root) {
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
		return child;
	}

	protected IDiffContainer getFileParent(IDiffContainer root,
			String gitPath) {
		IDiffContainer child = root;
		IPath path = Path.fromPortableString(gitPath);
		int pathLength = path.segmentCount() - 1;
		for (int i = 0; i < pathLength; i++) {
			child = getOrCreateChild(child, path.segment(i), false);
		}
		if (child != root) {
			if (!repository.isBare()) {
				path = Path
						.fromOSString(
								repository.getWorkTree().getAbsolutePath())
						.append(path);
			}
			path = path.removeLastSegments(1);
			IDiffContainer folder = child;
			while (folder != root) {
				if (folder instanceof FolderNode) {
					if (pathLength > 0) {
						((FolderNode) folder).setPath(path);
						path = path.removeLastSegments(1);
						pathLength--;
					} else {
						break;
					}
				}
				folder = folder.getParent();
			}
		}
