		synchronized (this) {
			if (snapshot == null) {
				snapshot = FileSnapshot.save(indexFile);
			} else if (snapshot.isModified(indexFile)) {
				notifyIndexChanged(false);
			}
