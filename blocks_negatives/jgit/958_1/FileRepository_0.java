	/**
	 * Construct a filename where the loose object having a specified SHA-1
	 * should be stored. If the object is stored in a shared repository the path
	 * to the alternative repo will be returned. If the object is not yet store
	 * a usable path in this repo will be returned. It is assumed that callers
	 * will look for objects in a pack first.
	 *
	 * @param objectId
	 * @return suggested file name
	 */
	public File toFile(final AnyObjectId objectId) {
		return objectDatabase.fileFor(objectId);
	}

