	/**
	 * Get the bytes for a key.
	 *
	 * @param k the key
	 * @return the bytes
	 */
	public static byte[] getKeyBytes(String k) {
		try {
			return k.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
