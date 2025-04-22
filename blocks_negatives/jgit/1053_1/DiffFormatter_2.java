	/**
	 * Construct a RawText sequence for use with {@link MyersDiff}.
	 *
	 * @param content
	 *            text to be compared.
	 * @return the raw text instance to handle the content.
	 */
	protected RawText newRawText(byte[] content) {
		return new RawText(content);
	}

