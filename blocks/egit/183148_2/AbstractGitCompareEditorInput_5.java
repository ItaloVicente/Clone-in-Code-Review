		if (child != root && !repository.isBare()) {
			path = Path.fromOSString(repository.getWorkTree().getAbsolutePath())
					.append(path).removeLastSegments(1);
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
