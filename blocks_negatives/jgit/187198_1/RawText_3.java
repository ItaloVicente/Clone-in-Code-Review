	/**
	 * Determine heuristically whether a byte array represents binary (as
	 * opposed to text) content.
	 *
	 * @param raw
	 *            the raw file content.
	 * @return true if raw is likely to be a binary file, false otherwise
	 */
	public static boolean isBinary(byte[] raw) {
		return isBinary(raw, raw.length);
	}

