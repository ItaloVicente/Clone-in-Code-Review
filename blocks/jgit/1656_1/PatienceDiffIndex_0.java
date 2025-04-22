	private static int tableBits(final int sz) {
		int bits = 31 - Integer.numberOfLeadingZeros(sz);
		if (bits == 0)
			bits = 1;
		if (1 << bits < sz)
			bits++;
		return bits;
