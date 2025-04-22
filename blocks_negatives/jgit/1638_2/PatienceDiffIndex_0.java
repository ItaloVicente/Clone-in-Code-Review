		final int blockCnt = region.getLengthB();
		if (blockCnt < 1) {
			table = new int[] {};
			tableMask = 0;

			hash = new int[] {};
			ptrs = new long[] {};
			next = new int[] {};
