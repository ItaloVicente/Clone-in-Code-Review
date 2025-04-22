
	public PushCommand setTransportConfigCallback(
			TransportConfigCallback transportConfigCallback) {
		checkCallable();
		this.transportConfigCallback = transportConfigCallback;
		return this;
	}
