	public ObjectId findObject(long offset) {
		final int ith = binarySearch(offset);
		if (ith < 0)
			return null;
		return index.getObjectId(nth[ith]);
	}
