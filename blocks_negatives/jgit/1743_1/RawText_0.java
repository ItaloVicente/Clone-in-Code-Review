		this(RawTextComparator.DEFAULT, input);
	}

	/**
	 * Create a new sequence from an existing content byte array.
	 *
	 * The entire array (indexes 0 through length-1) is used as the content.
	 *
	 * @param cmp
	 *            comparator that will later be used to compare texts.
	 * @param input
	 *            the content array. The array is never modified, so passing
	 *            through cached arrays is safe.
	 */
	public RawText(RawTextComparator cmp, byte[] input) {
