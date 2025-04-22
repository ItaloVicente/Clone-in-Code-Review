
	interface StatsRecorder {
		void recordHits(int count);

		void recordMisses(int count);

		void recordLoadSuccess(long loadTimeNanos);

		void recordLoadFailure(long loadTimeNanos);

		void recordEvictions(int count);

		void recordOpenFiles(int count);

		void recordOpenBytes(int count);

		@NonNull
		WindowCacheStats snapshot();
	}

	static class StatsRecorderImpl implements StatsRecorder {
		private final LongAdder hitCount;
		private final LongAdder missCount;
		private final LongAdder loadSuccessCount;
		private final LongAdder loadFailureCount;
		private final LongAdder totalLoadTime;
		private final LongAdder evictionCount;
		private final LongAdder openFileCount;
		private final LongAdder openByteCount;

		public StatsRecorderImpl() {
		    hitCount = new LongAdder();
		    missCount = new LongAdder();
		    loadSuccessCount = new LongAdder();
		    loadFailureCount = new LongAdder();
		    totalLoadTime = new LongAdder();
		    evictionCount = new LongAdder();
			openFileCount = new LongAdder();
			openByteCount = new LongAdder();
		  }

		@Override
		public void recordHits(int count) {
			hitCount.add(count);
		}

		@Override
		public void recordMisses(int count) {
			missCount.add(count);
		}

		@Override
		public void recordLoadSuccess(long loadTimeNanos) {
			loadSuccessCount.increment();
			totalLoadTime.add(loadTimeNanos);
		}

		@Override
		public void recordLoadFailure(long loadTimeNanos) {
			loadFailureCount.increment();
			totalLoadTime.add(loadTimeNanos);
		}

		@Override
		public void recordEvictions(int count) {
			evictionCount.add(count);
		}

		@Override
		public void recordOpenFiles(int count) {
			openFileCount.add(count);
		}

		@Override
		public void recordOpenBytes(int count) {
			openByteCount.add(count);
		}

		@Override
		public WindowCacheStats snapshot() {
			return new WindowCacheStats(hitCount.sum()
					loadSuccessCount.sum()
					totalLoadTime.sum()
					openFileCount.sum()
		}
	}

