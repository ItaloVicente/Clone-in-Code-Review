	/**
	 * Puts a value into a specific location in a byte buffer.
	 * @param buffer The buffer that the value will be written to.
	 * @param offset The offset for where the value begins in the buffer.
	 * @param length The length of the field in the array
	 * @param l The value to be encoded into the byte array
	 */
	public static void valueToFieldOffest(byte[] buffer, int offset, int length, long l) {
		long divisor;
		for (int i = 0; i < length; i++) {
			divisor = (long)Math.pow(256.0, (double) (length - 1 - i));
			buffer[offset + i] = (byte) (l / divisor);
			l = l % divisor;
		}
	}
