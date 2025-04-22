	public RawTextIgnoreLeadingWhitespace(byte[] input) {
		super(input);
	}

	@Override
	public boolean equals(final int i, final Sequence other, final int j) {
		return equals(this, i + 1, (RawText) other, j + 1);
	}

	private static boolean equals(final RawText a, final int ai,
			final RawText b, final int bi) {
		if (a.hashes[ai] != b.hashes[bi])
			return false;

		int as = a.lines.get(ai);
		int bs = b.lines.get(bi);
		int ae = a.lines.get(ai + 1);
		int be = b.lines.get(bi + 1);
