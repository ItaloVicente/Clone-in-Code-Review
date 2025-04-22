	public void waitUntilNotRacy(Path path) {
		if (!isModified(path.toFile())) {
			try {
				TimeUnit.NANOSECONDS.sleep(fsTimestampResolution.toNanos());
			} catch (InterruptedException e) {
			}
		}
	}

