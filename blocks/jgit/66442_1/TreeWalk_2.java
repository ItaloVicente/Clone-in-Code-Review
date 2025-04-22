	@Override
	public StreamType getStreamType() {
		if (streamTypeManager == null) {
			streamTypeManager = new StreamTypeManager(
					config.get(WorkingTreeOptions.KEY)
		}
		return streamTypeManager.getStreamType();
	}

