		return new FilterSpec(blobLimit);
	}

	static FilterSpec withBlobLimit(long blobLimit) {
		if (blobLimit < 0) {
			throw new IllegalArgumentException(
		}
		return new FilterSpec(blobLimit);
	}

	public static final FilterSpec NO_FILTER = new FilterSpec(-1);

	public long getBlobLimit() {
