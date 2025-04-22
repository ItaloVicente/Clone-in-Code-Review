	public List<String> getPushOptions() {
		if (!allowPushOptions) {
			throw new IllegalStateException();
		}
		if (enabledCapabilities == null) {
			throw new RequestNotYetReadException();
		}
		if (pushOptions == null) {
			return null;
		}
		return Collections.unmodifiableList(pushOptions);
	}
