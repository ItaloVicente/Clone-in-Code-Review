
	/**
	 * @param transportConfigCallback
	 *            if set, the callback will be invoked after the Transport has
	 *            created, but before the Transport is used. The callback can
	 *            use this opportunity to set additional type-specific
	 *            configuration on the Transport instance.
	 * @return {@code this}
	 */
	public CloneCommand setTransportConfigCallback(
			TransportConfigCallback transportConfigCallback) {
		this.transportConfigCallback = transportConfigCallback;
		return this;
	}
