
	@Override
	public int getRawType() {
		return Constants.OBJ_OFS_DELTA;
	}

	@Override
	ObjectId getDeltaBase() throws IOException {
		final ObjectId id = pack.findObjectForOffset(deltaBase);
		if (id == null)
			throw new CorruptObjectException(
					JGitText.get().offsetWrittenDeltaBaseForObjectNotFoundInAPack);
		return id;
	}
