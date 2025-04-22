		return insert(type, data, off, len, false);
	}

	/**
	 * Insert a loose object into the database.  If createDuplicate is
	 * true, write the loose object even if we already have it in the
	 * loose or packed ODB.
	 */
	private ObjectId insert(
			int type, byte[] data, int off, int len, boolean createDuplicate)
			throws IOException {
