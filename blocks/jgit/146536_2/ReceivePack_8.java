	protected boolean isCapabilityEnabled(String name) {
		return enabledCapabilities.contains(name);
	}

	void checkRequestWasRead() {
		if (enabledCapabilities == null)
			throw new RequestNotYetReadException();
