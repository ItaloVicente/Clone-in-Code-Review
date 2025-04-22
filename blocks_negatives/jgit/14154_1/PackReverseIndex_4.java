		if (offset <= Integer.MAX_VALUE) {
			final int i32 = Arrays.binarySearch(offsets32, (int) offset);
			if (i32 < 0)
				return -1;
			return i32;
		} else {
			final int i64 = Arrays.binarySearch(offsets64, offset);
			if (i64 < 0)
				return -1;
			return nth32.length + i64;
