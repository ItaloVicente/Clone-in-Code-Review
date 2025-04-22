	public int lockAndWait(int retries
		int attempts = 0;
		while (true) {
			attempts++;
			if (lock()) {
				break;
			}
			if (attempts < retries) {
				try {
					Thread.sleep(10);
					continue;
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					throw new LockFailedException(ref
				}
			}
			throw new LockFailedException(ref);
		}
		if (forAppend) {
			copyCurrentContent();
		}
		return attempts;
	}

