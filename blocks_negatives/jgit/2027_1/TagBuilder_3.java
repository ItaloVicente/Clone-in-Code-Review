		return build(new ObjectInserter.Formatter());
	}

	/**
	 * Format this builder's state as an annotated tag object.
	 *
	 * As a side effect, {@link #getTagId()} will be populated with the proper
	 * ObjectId for the formatted content.
	 *
	 * @param oi
	 *            the inserter whose formatting support will be reused. The
	 *            inserter itself is not affected, and the annotated tag is not
	 *            actually inserted into the repository.
	 * @return this object in the canonical annotated tag format, suitable for
	 *         storage in a repository.
	 */
	public byte[] build(ObjectInserter oi) {
