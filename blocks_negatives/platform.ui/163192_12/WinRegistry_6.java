	/**
	 * Converts a null-terminated byte array to java string
	 *
	 * @throws UnsupportedEncodingException
	 */
	private static String byteArrayToString(byte[] array) throws UnsupportedEncodingException {
		byte[] truncatedArray = new byte[array.length - 1];
		System.arraycopy(array, 0, truncatedArray, 0, truncatedArray.length);
		String result;
		try {
			result = new String(truncatedArray, "Windows-1252"); //$NON-NLS-1$
		} catch (UnsupportedEncodingException e) {
			result = new String(truncatedArray, "ISO-8859-1"); //$NON-NLS-1$
		}
		return result;
	}

	private static String toString(byte[] bytes) {
