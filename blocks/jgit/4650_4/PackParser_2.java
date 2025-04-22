	private final void checkIfTooLarge(long size) throws IOException {
		if (0 < maxObjectSizeLimit && maxObjectSizeLimit < size) {
			throw new TooLargeObjectInPackException();
		}
	}

