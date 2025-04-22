	@Nullable
	public List<String> getPushOptions() {
		if (allowPushOptions) {
			if (enabledCapabilities == null) {
				throw new RequestNotYetReadException();
			}

			return pushOptions == null ? null
					: Collections.unmodifiableList(pushOptions);
		} else {
			throw new IllegalStateException();
