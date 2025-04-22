		return getEolStreamType(null);
	}

	/**
	 * @param opType
	 *            The operationtype (checkin/checkout) which should be used
	 * @return the eol stream type for the current entry or <code>null</code> if
	 *         it cannot be determined. When state or state.walk is null or the
	 *         {@link TreeWalk} is not based on a {@link Repository} then null
	 *         is returned.
	 * @throws IOException
	 */
	private EolStreamType getEolStreamType(OperationType opType)
			throws IOException {
