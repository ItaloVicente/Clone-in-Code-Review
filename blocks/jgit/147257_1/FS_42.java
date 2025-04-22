		private static TimeUnit getUnit(long nanos) {
			TimeUnit unit;
			if (nanos < 200_000L) {
				unit = TimeUnit.NANOSECONDS;
			} else if (nanos < 200_000_000L) {
				unit = TimeUnit.MICROSECONDS;
			} else {
				unit = TimeUnit.MILLISECONDS;
			}
			return unit;
		}

		private final @NonNull Duration fsTimestampResolution;

		private Duration minimalRacyInterval;

		public Duration getMinimalRacyInterval() {
			return minimalRacyInterval;
		}

		@NonNull
		public Duration getFsTimestampResolution() {
			return fsTimestampResolution;
		}

		public FileStoreAttributes(
				@NonNull Duration fsTimestampResolution) {
			this.fsTimestampResolution = fsTimestampResolution;
			this.minimalRacyInterval = Duration.ZERO;
		}

		@SuppressWarnings({ "nls"
