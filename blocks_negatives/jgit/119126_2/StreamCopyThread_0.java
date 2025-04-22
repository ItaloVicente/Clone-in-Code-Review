	/**
	 * Request the thread to flush the output stream as soon as possible.
	 * <p>
	 * This is an asynchronous request to the thread. The actual flush will
	 * happen at some future point in time, when the thread wakes up to process
	 * the request.
	 */
	@Deprecated
	public void flush() {
		synchronized (writeLock) {
			interrupt();
		}
	}

