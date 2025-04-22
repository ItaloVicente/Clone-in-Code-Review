	private RevObject parseAnyOrNull(final AnyObjectId id) {
		if (id == null)
			return null;
		try {
			return walk.parseAny(id);
		} catch (IOException e) {
			return null;
		}
	}

	private void advertiseAnyOnce(final RevObject obj, final String refName)
