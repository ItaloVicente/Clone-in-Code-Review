	boolean loadNextBatch(int currentIndex) {
		synchronized (lock) {
			if (hasMore && currentIndex + (BATCH_SIZE / 2) > size
					&& currentIndex > nextLoadHint) {
				nextLoadHint = currentIndex;
				return true;
			}
		}
		return false;
