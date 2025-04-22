	/**
	 * Include references of alternate repositories as {@code .have} lines.
	 *
	 * @param src
	 *            repository to get the additional reachable objects from.
	 * @throws IOException
	 *             the underlying output stream failed to write out an
	 *             advertisement record.
	 */
	public void includeAdditionalHaves(Repository src) throws IOException {
		for (ObjectId id : src.getAdditionalHaves())
			advertiseHave(id);
	}

