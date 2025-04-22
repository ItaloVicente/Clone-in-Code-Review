		try {
			FileSnapshot[] lastSnapshot = { null };
			Boolean wasRead = FileUtils.readWithRetries(getFile()
				final FileSnapshot oldSnapshot = snapshot;
				final FileSnapshot newSnapshot;
				newSnapshot = FileSnapshot.saveNoConfig(f);
				lastSnapshot[0] = newSnapshot;
				final byte[] in = IO.readFully(f);
