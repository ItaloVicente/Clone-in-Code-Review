		final FileSnapshot oldSnapshot = snapshot;
		final FileSnapshot newSnapshot = FileSnapshot.save(getFile());
		try {
			final byte[] in = IO.readFully(getFile());
			final ObjectId newHash = hash(in);
			if (hash.equals(newHash)) {
				if (oldSnapshot.equals(newSnapshot))
					oldSnapshot.setClean(newSnapshot);
				else
					snapshot = newSnapshot;
			} else {
				final String decoded;
				if (isUtf8(in)) {
					decoded = RawParseUtils.decode(CHARSET,
							in, 3, in.length);
					utf8Bom = true;
