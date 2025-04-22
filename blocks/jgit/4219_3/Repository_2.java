		IndexChangedListener l = new IndexChangedListener() {

			public void onIndexChanged(IndexChangedEvent event) {
				notifyIndexChanged();
			}
		};
		return DirCache.lock(getIndexFile()
