	 *
	 * @param charsetGuess
	 *            optional array to suggest the character set to use when
	 *            decoding each file's line. If supplied the array must have a
	 *            length of <code>{@link #getParentCount()} + 1</code>
	 *            representing the old revision character sets and the new
	 *            revision character set.
	 * @return the patch script, as a Unicode string.
