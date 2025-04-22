
	/**
	 * @return offset of object header within pack file
	 */
	final long getObjectOffset() {
		return objectOffset;
	}

	/**
	 * @return id of delta base object for this object representation. null if
	 *         object is not stored as delta.
	 * @throws IOException
	 *             when delta base cannot read.
	 */
	abstract ObjectId getDeltaBase() throws IOException;
