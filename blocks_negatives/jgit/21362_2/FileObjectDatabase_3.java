	/**
	 * Does the requested object exist in this database?
	 * <p>
	 * Alternates (if present) are searched automatically.
	 *
	 * @param objectId
	 *            identity of the object to test for existence of.
	 * @return true if the specified object is stored in this database, or any
	 *         of the alternate databases.
	 */
	public boolean has(final AnyObjectId objectId) {
		return hasObjectImpl1(objectId) || hasObjectImpl2(objectId.name());
	}

	/**
	 * Compute the location of a loose object file.
	 *
	 * @param objectId
	 *            identity of the loose object to map to the directory.
	 * @return location of the object, if it were to exist as a loose object.
	 */
	File fileFor(final AnyObjectId objectId) {
		return fileFor(objectId.name());
	}

	File fileFor(final String objectName) {
		final String d = objectName.substring(0, 2);
		final String f = objectName.substring(2);
		return new File(new File(getDirectory(), d), f);
	}

	final boolean hasObjectImpl1(final AnyObjectId objectId) {
		if (hasObject1(objectId))
			return true;

		for (final AlternateHandle alt : myAlternates()) {
			if (alt.db.hasObjectImpl1(objectId))
				return true;
		}

		return tryAgain1() && hasObject1(objectId);
	}

	final boolean hasObjectImpl2(final String objectId) {
		if (hasObject2(objectId))
			return true;

		for (final AlternateHandle alt : myAlternates()) {
			if (alt.db.hasObjectImpl2(objectId))
				return true;
		}

		return false;
	}

