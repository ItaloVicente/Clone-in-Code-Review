	@Override
	public StreamType getStreamType() {
		if (streamTypeProvider == null)
			throw new IllegalStateException(
		return streamTypeProvider.getStreamType();
	}

