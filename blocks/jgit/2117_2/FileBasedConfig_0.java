			final byte[] in = IO.readFully(getFile());
			final ObjectId newHash = hash(in);
			if (hash.equals(newHash)) {
				if (oldSnapshot.equals(newSnapshot))
					oldSnapshot.setClean(newSnapshot);
				else
					snapshot = newSnapshot;
			} else {
				fromText(RawParseUtils.decode(in));
				snapshot = newSnapshot;
				hash = newHash;
			}
