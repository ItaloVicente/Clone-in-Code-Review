		encoding = Charset.forName(encodingName);
	}

	/**
	 * Set the encoding for the commit information.
	 *
	 * @param enc
	 *            the encoding to use.
	 */
	public void setEncoding(Charset enc) {
		encoding = enc;
