	/**
	 * Get the result from the invocation.
	 *
	 * @return the result
	 * @throws Throwable if an error occurred when evaluating the callable
	 */
	public T getResult() throws Throwable {
		latch.await();
		if(throwable != null) {
			throw throwable;
		}
		return rv;
	}
