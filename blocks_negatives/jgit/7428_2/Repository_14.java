	public DirCache lockDirCache() throws NoWorkTreeException,
			CorruptObjectException, IOException {
		IndexChangedListener l = new IndexChangedListener() {

			public void onIndexChanged(IndexChangedEvent event) {
				notifyIndexChanged();
			}
		};
		return DirCache.lock(this, l);
	}
