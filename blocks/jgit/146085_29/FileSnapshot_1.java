	private long getEffectiveRacyThreshold() {
		long timestampResolution = fileStoreAttributeCache
				.getFsTimestampResolution().toNanos();
		long minRacyInterval = fileStoreAttributeCache.getMinimalRacyInterval()
				.toNanos();
		return Math.max(timestampResolution
	}

