		if (offset <= Integer.MAX_VALUE) {
			final int i32 = Arrays.binarySearch(offsets32, (int) offset);
			if (i32 < 0)
				return null;
			return index.getObjectId(nth32[i32]);
		} else {
			final int i64 = Arrays.binarySearch(offsets64, offset);
			if (i64 < 0)
				return null;
			return index.getObjectId(nth64[i64]);
		}
