	private boolean isRacyClean(Instant read) {
		racyThreshold = getEffectiveRacyThreshold();
		delta = Duration.between(lastModified
		wasRacyClean = delta <= racyThreshold;
		if (LOG.isDebugEnabled()) {
			LOG.debug(
					"file={}
					file
					dateFmt.format(lastModified)
					Long.valueOf(racyThreshold));
		}
		return wasRacyClean;
	}

	private long getEffectiveRacyThreshold() {
		long timestampResolution = fileStoreAttributeCache
				.getFsTimestampResolution().toNanos();
		long minRacyInterval = fileStoreAttributeCache.getMinimalRacyInterval()
				.toNanos();
		long max = Math.max(timestampResolution
		return max < 100_000_000L ? max * 5 / 2 : max * 5 / 4;
