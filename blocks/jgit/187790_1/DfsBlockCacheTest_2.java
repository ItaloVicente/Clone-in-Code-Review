
	private void waitForExecutorPoolTermination() throws Exception {
		pool.shutdown();
		pool.awaitTermination(500
		assertTrue("Threads did not complete
				pool.isTerminated());
	}
