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
	public final ObjectLoader openObject(final WindowCursor curs,
			final AnyObjectId objectId) throws IOException {
		ObjectLoader ldr;

		ldr = openObjectImpl1(curs, objectId);
		if (ldr != null) {
			return ldr;
		}

		ldr = openObjectImpl2(curs, objectId.name(), objectId);
		if (ldr != null) {
			return ldr;
		}
		return null;
	}

	private ObjectLoader openObjectImpl1(final WindowCursor curs,
			final AnyObjectId objectId) throws IOException {
		ObjectLoader ldr;

		ldr = openObject1(curs, objectId);
		if (ldr != null) {
			return ldr;
		}
		for (final ObjectDatabase alt : getAlternates()) {
			ldr = alt.openObjectImpl1(curs, objectId);
			if (ldr != null) {
				return ldr;
			}
		}
		if (tryAgain1()) {
			ldr = openObject1(curs, objectId);
			if (ldr != null) {
				return ldr;
			}
		}
		return null;
	}

	private ObjectLoader openObjectImpl2(final WindowCursor curs,
			final String objectName, final AnyObjectId objectId)
			throws IOException {
		ObjectLoader ldr;

		ldr = openObject2(curs, objectName, objectId);
		if (ldr != null) {
			return ldr;
		}
		for (final ObjectDatabase alt : getAlternates()) {
			ldr = alt.openObjectImpl2(curs, objectName, objectId);
			if (ldr != null) {
				return ldr;
			}
		}
		return null;
	}

	/**
	 * Fast half of {@link #openObject(WindowCursor, AnyObjectId)}.
