	/**
	 * Seek either to a reference, or a reference subtree.
	 * <p>
	 * If {@code refName} ends with {@code "/"} the method will seek to the
	 * subtree of all references starting with {@code refName} as a prefix.
	 * <p>
	 * Otherwise, only {@code refName} will be found, if present.
	 *
	 * @param refName
	 *            reference name or subtree to find.
	 * @throws IOException
	 *             reftable cannot be read.
	 */
