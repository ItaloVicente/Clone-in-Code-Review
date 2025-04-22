
		return lcs;
	}

	private static boolean isDuplicate(long rec) {
		return (((int) rec) & DUPLICATE_MASK) != 0;
	}

	private static int aOfRaw(long rec) {
		return ((int) (rec >>> A_SHIFT)) & PTR_MASK;
	}

	private static int aOf(long rec) {
		return aOfRaw(rec) - 1;
	}

	private static int bOf(long rec) {
		return (int) (rec >>> B_SHIFT);
