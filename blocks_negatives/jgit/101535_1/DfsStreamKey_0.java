	public static DfsStreamKey of(String name) {
		return of(name.getBytes(UTF_8));
	}

	/**
	 * @param name
	 *            compute the key from a byte array. The key takes ownership of
	 *            the passed {@code byte[] name}.
	 * @return key for {@code name}
	 */
	public static DfsStreamKey of(byte[] name) {
		return new ByteArrayDfsStreamKey(name);
