	private long getEffectiveRacyThreshold() {
		long timestampResolution = fileStoreAttributeCache
				.getFsTimestampResolution().toNanos();
		if (timestampResolution < 100_000_000L) {
			timestampResolution = timestampResolution * 13 / 10;
		}
		long minRacyInterval = fileStoreAttributeCache.getMinimalRacyInterval()
				.toNanos() * 13 / 10;
		return Math.max(timestampResolution
	}

