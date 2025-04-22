	/**
	 * @param timeout
	 *            the timeout used for the push operation
	 * @return {@code this}
	 */
	public PushCommand setTimeout(int timeout) {
		checkCallable();
		this.timeout = timeout;
		return this;
	}

