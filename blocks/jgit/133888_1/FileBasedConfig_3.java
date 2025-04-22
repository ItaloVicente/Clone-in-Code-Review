				}
			}
			final ObjectId newHash = hash(in);
			if (hash.equals(newHash)) {
				if (oldSnapshot.equals(newSnapshot)) {
					oldSnapshot.setClean(newSnapshot);
