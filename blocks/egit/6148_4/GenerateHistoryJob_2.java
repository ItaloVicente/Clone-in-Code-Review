	void setLoadHint(final int index) {
		itemToLoad = index;
	}

	void setLoadHint(final RevCommit c) {
		commitToLoad = c;
	}

	int loadMoreItemsThreshold() {
		return loadedCommits.size() - (BATCH_SIZE / 2);
	}
