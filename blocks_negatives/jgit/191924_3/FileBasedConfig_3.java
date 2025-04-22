		final int maxRetries = 5;
		int retryDelayMillis = 20;
		int retries = 0;
		while (true) {
			final FileSnapshot oldSnapshot = snapshot;
			final FileSnapshot newSnapshot;
			newSnapshot = FileSnapshot.saveNoConfig(getFile());
			try {
				final byte[] in = IO.readFully(getFile());
