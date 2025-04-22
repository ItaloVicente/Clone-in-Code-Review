	/**
	 * Compute the location of a loose object file.
	 *
	 * @param objectId
	 *            identity of the loose object to map to the directory.
	 * @return location of the object, if it were to exist as a loose object.
	 */
	@Override
	public File fileFor(final AnyObjectId objectId) {
		return super.fileFor(objectId);
	}

