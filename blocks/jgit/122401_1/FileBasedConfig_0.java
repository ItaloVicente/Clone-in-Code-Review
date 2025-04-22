		final int maxStaleRetries = 5;
		int retries = 0;
		while (true) {
			final FileSnapshot oldSnapshot = snapshot;
			final FileSnapshot newSnapshot = FileSnapshot.save(getFile());
			try {
				final byte[] in = IO.readFully(getFile());
				final ObjectId newHash = hash(in);
				if (hash.equals(newHash)) {
					if (oldSnapshot.equals(newSnapshot)) {
						oldSnapshot.setClean(newSnapshot);
					} else {
						snapshot = newSnapshot;
					}
