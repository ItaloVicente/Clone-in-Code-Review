	/**
	 * Wait for the barrier, invoke the callable and capture the result or an
	 * exception.
	 */
	@Override
	public void run() {
		try {
			barrier.await();
			rv=callable.call();
		} catch(Throwable t) {
			throwable=t;
		}
		latch.countDown();
	}
