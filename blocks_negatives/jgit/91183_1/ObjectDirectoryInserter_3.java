		return insert(type, len, is, false);
	}

	/**
	 * Insert a loose object into the database.  If createDuplicate is
	 * true, write the loose object even if we already have it in the
	 * loose or packed ODB.
	 */
	ObjectId insert(int type, long len, InputStream is, boolean createDuplicate)
			throws IOException {
