
	@Override
	public final boolean isLarge() {
		return false;
	}

	@Override
	public final ObjectStream openStream() throws MissingObjectException
			IOException {
		return new ObjectStream.SmallStream(this);
	}
