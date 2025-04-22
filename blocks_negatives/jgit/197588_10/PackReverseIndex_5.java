	private int binarySearch(long offset) {
		int bucket = (int) (offset / bucketSize);
		int low = bucket == 0 ? 0 : offsetIndex[bucket - 1];
		int high = offsetIndex[bucket];
		while (low < high) {
			final int mid = (low + high) >>> 1;
			final long o = index.getOffset(nth[mid]);
			if (offset < o)
				high = mid;
			else if (offset == o)
				return mid;
			else
				low = mid + 1;
		}
		return -1;
	}

	ObjectId findObjectByPosition(int nthPosition) {
		return index.getObjectId(nth[nthPosition]);
	}
