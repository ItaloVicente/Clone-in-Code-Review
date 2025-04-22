	protected PackedObjectLoader getBaseLoader(final WindowCursor curs)
			throws IOException {
		final PackedObjectLoader or = pack.get(curs, deltaBase);
		if (or == null)
			throw new MissingObjectException(deltaBase, "delta base");
		return or;
