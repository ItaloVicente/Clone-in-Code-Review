		return build(new ObjectInserter.Formatter());
	}

	/**
	 * Format this builder's state as a commit object.
	 *
	 * As a side effect, {@link #getCommitId()} will be populated with the
	 * proper ObjectId for the formatted content.
	 *
	 * @param oi
	 *            the inserter whose formatting support will be reused. The
	 *            inserter itself is not affected, and the commit is not
	 *            actually inserted into the repository.
	 * @return this object in the canonical commit format, suitable for storage
	 *         in a repository.
	 * @throws UnsupportedEncodingException
	 *             the encoding specified by {@link #getEncoding()} is not
	 *             supported by this Java runtime.
	 */
	public byte[] build(ObjectInserter oi) throws UnsupportedEncodingException {
