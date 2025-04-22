	public PullCommand setTransportConfigCallback(
			TransportConfigCallback transportConfigCallback) {
		checkCallable();
		this.transportConfigCallback = transportConfigCallback;
		return this;
	}

