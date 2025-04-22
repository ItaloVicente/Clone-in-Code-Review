	public ObjectId getDeltaBase() throws IOException {
		final ObjectId id = pack.findObjectForOffset(deltaBase);
		if (id == null)
			throw new CorruptObjectException(
					JGitText.get().offsetWrittenDeltaBaseForObjectNotFoundInAPack);
		return id;
