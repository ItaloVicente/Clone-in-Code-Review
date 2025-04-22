	/**
	 * Tries to determine a Gerrit change number from an input string.
	 *
	 * @param input
	 *            string to derive a change number from
	 * @return the change number and possibly also the patch set number, or
	 *         {@code null} if none could be determined.
	 */
	protected static Change determineChangeFromString(String input) {
