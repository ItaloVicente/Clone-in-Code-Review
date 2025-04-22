	public static final long parseHexInt64(final byte[] bs
		long r = digits16[bs[p]] << 4;

		r |= digits16[bs[p + 1]];
		r <<= 4;

		r |= digits16[bs[p + 2]];
		r <<= 4;

		r |= digits16[bs[p + 3]];
		r <<= 4;

		r |= digits16[bs[p + 4]];
		r <<= 4;

		r |= digits16[bs[p + 5]];
		r <<= 4;

		r |= digits16[bs[p + 6]];
		r <<= 4;

		r |= digits16[bs[p + 7]];
		r <<= 4;

		r |= digits16[bs[p + 8]];
		r <<= 4;

		r |= digits16[bs[p + 9]];
		r <<= 4;

		r |= digits16[bs[p + 10]];
		r <<= 4;

		r |= digits16[bs[p + 11]];
		r <<= 4;

		r |= digits16[bs[p + 12]];
		r <<= 4;

		r |= digits16[bs[p + 13]];
		r <<= 4;

		r |= digits16[bs[p + 14]];

		final int last = digits16[bs[p + 15]];
		if (r < 0 || last < 0)
			throw new ArrayIndexOutOfBoundsException();
		return (r << 4) | last;
	}

