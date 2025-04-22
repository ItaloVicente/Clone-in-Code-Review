
		@Override
		public long getHitCount() {
			return hitCount.sum();
		}

		@Override
		public long getMissCount() {
			return missCount.sum();
		}

		@Override
		public long getLoadSuccessCount() {
			return loadSuccessCount.sum();
		}

		@Override
		public long getLoadFailureCount() {
			return loadFailureCount.sum();
		}

		@Override
		public long getEvictionCount() {
			return evictionCount.sum();
		}

		@Override
		public long getTotalLoadTime() {
			return totalLoadTime.sum();
		}

		@Override
		public long getOpenFileCount() {
			return openFileCount.sum();
		}

		@Override
		public long getOpenByteCount() {
			return openByteCount.sum();
		}

		@Override
		public void resetCounters() {
			hitCount.reset();
			missCount.reset();
			loadSuccessCount.reset();
			loadFailureCount.reset();
			totalLoadTime.reset();
			evictionCount.reset();
		}
