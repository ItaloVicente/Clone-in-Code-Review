	/**
	 * Open an object from this database.
	 * <p>
	 * Alternates (if present) are searched automatically.
	 *
	 * @param curs
	 *            temporary working space associated with the calling thread.
	 * @param objectId
	 *            identity of the object to open.
	 * @return a {@link ObjectLoader} for accessing the data of the named
	 *         object, or null if the object does not exist.
	 * @throws IOException
	 */
	ObjectLoader openObject(final WindowCursor curs, final AnyObjectId objectId)
			throws IOException {
		ObjectLoader ldr;

		ldr = openObjectImpl1(curs, objectId);
		if (ldr != null)
			return ldr;

		ldr = openObjectImpl2(curs, objectId.name(), objectId);
		if (ldr != null)
			return ldr;

		return null;
	}

	final ObjectLoader openObjectImpl1(final WindowCursor curs,
			final AnyObjectId objectId) throws IOException {
		ObjectLoader ldr;

		ldr = openObject1(curs, objectId);
		if (ldr != null)
			return ldr;

		for (final AlternateHandle alt : myAlternates()) {
			ldr = alt.db.openObjectImpl1(curs, objectId);
			if (ldr != null)
				return ldr;
		}

		if (tryAgain1()) {
			ldr = openObject1(curs, objectId);
			if (ldr != null)
				return ldr;
		}

		return null;
	}

	final ObjectLoader openObjectImpl2(final WindowCursor curs,
			final String objectName, final AnyObjectId objectId)
			throws IOException {
		ObjectLoader ldr;

		ldr = openObject2(curs, objectName, objectId);
		if (ldr != null)
			return ldr;

		for (final AlternateHandle alt : myAlternates()) {
			ldr = alt.db.openObjectImpl2(curs, objectName, objectId);
			if (ldr != null)
				return ldr;
		}

		return null;
	}

	long getObjectSize(WindowCursor curs, AnyObjectId objectId)
			throws IOException {
		long sz = getObjectSizeImpl1(curs, objectId);
		if (0 <= sz)
			return sz;
		return getObjectSizeImpl2(curs, objectId.name(), objectId);
	}

	final long getObjectSizeImpl1(final WindowCursor curs,
			final AnyObjectId objectId) throws IOException {
		long sz;

		sz = getObjectSize1(curs, objectId);
		if (0 <= sz)
			return sz;

		for (final AlternateHandle alt : myAlternates()) {
			sz = alt.db.getObjectSizeImpl1(curs, objectId);
			if (0 <= sz)
				return sz;
		}

		if (tryAgain1()) {
			sz = getObjectSize1(curs, objectId);
			if (0 <= sz)
				return sz;
		}

		return -1;
	}

	final long getObjectSizeImpl2(final WindowCursor curs,
			final String objectName, final AnyObjectId objectId)
			throws IOException {
		long sz;

		sz = getObjectSize2(curs, objectName, objectId);
		if (0 <= sz)
			return sz;

		for (final AlternateHandle alt : myAlternates()) {
			sz = alt.db.getObjectSizeImpl2(curs, objectName, objectId);
			if (0 <= sz)
				return sz;
		}

		return -1;
	}

