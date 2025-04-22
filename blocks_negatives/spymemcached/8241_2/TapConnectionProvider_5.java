	private void checkState() {
		if(shuttingDown) {
			throw new IllegalStateException("Shutting down");
		}
		assert isAlive() : "IO Thread is not running.";
	}

