		boolean notify = false;
		synchronized (this) {
			if (snapshot == null) {
				snapshot = FileSnapshot.save(indexFile);
			} else if (snapshot.isModified(indexFile)) {
				notify = true;
			}
		}
		if (notify) {
