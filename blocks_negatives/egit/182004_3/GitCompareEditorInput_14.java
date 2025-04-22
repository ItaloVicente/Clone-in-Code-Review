	private IDiffContainer getFileParent(IDiffContainer root, String filePath) {
		IPath path = new Path(filePath);
		IDiffContainer child = root;
		if (diffRoots.isEmpty()) {
			for (int i = 0; i < path.segmentCount() - 1; i++)
				child = getOrCreateChild(child, path.segment(i));
			return child;
		} else {
			for (Entry<IPath, IDiffContainer> entry : diffRoots.entrySet()) {
				if (entry.getKey().isPrefixOf(path)) {
					for (int i = entry.getKey().segmentCount(); i < path
							.segmentCount() - 1; i++)
						child = getOrCreateChild(child, path.segment(i));
					return child;
