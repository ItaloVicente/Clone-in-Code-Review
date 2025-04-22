		}
		if (children != null && (isRoot || children.length > 1)) {
			for (IDiffElement node : children) {
				if (node instanceof FolderNode) {
					collapse((DiffContainer) node);
				}
