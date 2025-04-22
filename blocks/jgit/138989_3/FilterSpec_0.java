		return new FilterSpec(blobLimit);
	}

	public static FilterSpec blobFilter(long blobLimit) {
		if (blobLimit < 0) {
			throw new IllegalArgumentException(
		}
		return new FilterSpec(blobLimit);
	}

	public static final FilterSpec NO_OP_FILTER = new FilterSpec(-1);

	public long getBlobLimit() {
