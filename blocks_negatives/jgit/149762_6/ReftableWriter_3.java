	/**
	 * Begin writing the reftable. Should be called only once.
	 *
	 * @param os
	 *            stream to write the table to. Caller is responsible for
	 *            closing the stream after invoking {@link #finish()}.
	 * @return {@code this}
	 */
	public ReftableWriter begin(OutputStream os) {
		if (outputStream != null) {
		}
		outputStream = os;
		return begin();
	}

