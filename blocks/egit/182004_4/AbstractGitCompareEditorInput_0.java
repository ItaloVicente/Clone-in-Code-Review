	protected IDiffContainer getFileParent(IDiffContainer root,
			String gitPath) {
		IDiffContainer child = root;
		IPath path = Path.fromPortableString(gitPath);
		for (int i = 0; i < path.segmentCount() - 1; i++) {
			child = getOrCreateChild(child, path.segment(i), false);
		}
		return child;
	}

