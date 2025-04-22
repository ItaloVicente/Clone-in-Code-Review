		return new FilterSpec(blobLimit
	}

	static FilterSpec withTreeDepthLimit(long treeDepthLimit) {
		if (treeDepthLimit < 0) {
			throw new IllegalArgumentException(
		}
		return new FilterSpec(-1
