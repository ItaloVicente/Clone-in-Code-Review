	/**
	 * @param timeout
	 *            the timeout used for the fetch operation
	 * @return {@code this}
	 */
	public FetchCommand setTimeout(int timeout) {
		checkCallable();
		this.timeout = timeout;
		return this;
	}

