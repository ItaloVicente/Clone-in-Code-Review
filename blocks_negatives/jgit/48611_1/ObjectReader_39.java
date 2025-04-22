	/**
	 * Release any resources used by this reader.
	 * <p>
	 * A reader that has been released can be used again, but may need to be
	 * released after the subsequent usage. Use {@link #close()} instead.
	 */
	@Deprecated
	public void release() {
		close();
	}

