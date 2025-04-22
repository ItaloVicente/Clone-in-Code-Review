	private long getEffectiveRacyThreshold() {
		long timestampResolution = fileStoreAttributeCache
				.getFsTimestampResolution().toNanos();
		if (timestampResolution < 100_000_000L) {
			timestampResolution = timestampResolution * 12 / 10;
		}
		long minRacyInterval = fileStoreAttributeCache.getMinimalRacyInterval()
				.toNanos() * 12 / 10;
		return Math.max(timestampResolution
	}

