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
