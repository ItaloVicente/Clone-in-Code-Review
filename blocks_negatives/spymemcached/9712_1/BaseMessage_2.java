	/**
	 * Holds the binary data for the header field.
	 */

	protected byte[] mbytes;
	/**
	 * Instantiates a tap header.
	 */
	protected BaseMessage() {
		mbytes = new byte[HEADER_LENGTH];
	}
