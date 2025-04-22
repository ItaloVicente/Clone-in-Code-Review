	/**
	 * Set the encoding for the commit information.
	 *
	 * @param enc
	 *            the encoding to use.
	 */
	public void setEncoding(Charset enc) {
		encoding = enc;
	}

	/**
	 * Get the encoding that should be used for the commit message text.
	 *
	 * @return the encoding that should be used for the commit message text.
	 */
	public Charset getEncoding() {
		return encoding;
	}

	/**
	 * Format this builder's state as a commit object.
	 *
	 * @return this object in the canonical commit format, suitable for storage
	 *         in a repository.
	 * @throws java.io.UnsupportedEncodingException
	 *             the encoding specified by {@link #getEncoding()} is not
	 *             supported by this Java runtime.
	 */
