		return new FilterSpec(blobLimit
	}

	public static FilterSpec treeDepthFilter(long treeDepthLimit) {
		if (treeDepthLimit < 0) {
			throw new IllegalArgumentException(
		}
		return new FilterSpec(-1
