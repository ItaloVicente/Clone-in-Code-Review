	public static int compareUInt64(final long a
		long cmp = (a >>> 1) - (b >>> 1);
		if (cmp > 0) {
			return 1;
		} else if (cmp < 0) {
			return -1;
		}
		cmp = ((a & 1) - (b & 1));
		if (cmp > 0) {
			return 1;
		} else if (cmp < 0) {
			return -1;
		} else {
			return 0;
		}
	}

