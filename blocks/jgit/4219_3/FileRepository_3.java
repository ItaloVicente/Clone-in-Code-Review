		else if (snapshot.isModified(indexFile))
			notifyIndexChanged();
	}

	@Override
	public void notifyIndexChanged() {
		snapshot = FileSnapshot.save(getIndexFile());
		fireEvent(new IndexChangedEvent());
