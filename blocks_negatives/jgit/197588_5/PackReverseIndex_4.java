		if (ith + 1 == nth.length)
			return maxOffset;
		return index.getOffset(nth[ith + 1]);
	}

	int findPosition(long offset) {
		return binarySearch(offset);
	}
