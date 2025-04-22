	public void halt() throws InterruptedException {
		for (;;) {
			if (isAlive()) {
				done = true;
				interrupt();
			} else
				break;
		}
	}

