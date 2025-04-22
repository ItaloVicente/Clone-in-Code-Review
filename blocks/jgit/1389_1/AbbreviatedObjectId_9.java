	public final int prefixCompare(final byte[] bs
		int cmp;

		cmp = NB.compareUInt32(w1
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt32(w2
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt32(w3
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt32(w4
		if (cmp != 0)
			return cmp;

		return NB.compareUInt32(w5
	}

	public final int prefixCompare(final int[] bs
		int cmp;

		cmp = NB.compareUInt32(w1
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt32(w2
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt32(w3
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt32(w4
		if (cmp != 0)
			return cmp;

		return NB.compareUInt32(w5
	}

	public final int getFirstByte() {
		return w1 >>> 24;
	}

