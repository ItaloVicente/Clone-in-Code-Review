		detectIndexChanges();
	}

	private void detectIndexChanges() {
		if (isBare())
			return;

		File indexFile = getIndexFile();
		if (snapshot == null)
			snapshot = FileSnapshot.save(indexFile);
		else if (snapshot.isModified(indexFile)) {
			snapshot = FileSnapshot.save(indexFile);
			fireEvent(new IndexChangedEvent());
		}
