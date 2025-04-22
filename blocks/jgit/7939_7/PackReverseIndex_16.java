
	int findPostion(long offset) {
		if (offset <= Integer.MAX_VALUE) {
			final int i32 = Arrays.binarySearch(offsets32
			if (i32 < 0)
				return -1;
			return i32;
		} else {
			final int i64 = Arrays.binarySearch(offsets64
			if (i64 < 0)
				return -1;
			return nth32.length + i64;
		}
	}

	ObjectId findObjectByPosition(int nthPosition) {
		if (nthPosition < nth32.length)
			return index.getObjectId(nth32[nthPosition]);
		final int i64 = nthPosition - nth32.length;
		return index.getObjectId(nth64[i64]);
	}
