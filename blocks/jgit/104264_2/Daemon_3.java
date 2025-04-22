			acceptThread.shutDown();
		}
	}

	public void stopAndWait() throws InterruptedException {
		Thread acceptor = null;
		synchronized (this) {
			acceptor = acceptThread;
			stop();
		}
		if (acceptor != null) {
			acceptor.join();
