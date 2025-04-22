	/**
	 * Release the internal ObjectReader state. Use {@link #close()} instead.
	 */
	@Deprecated
	public void release() {
		close();
	}

