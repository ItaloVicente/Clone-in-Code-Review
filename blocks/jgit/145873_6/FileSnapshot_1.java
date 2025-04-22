		long delta = (read - lastModified) * 1_000_000;
		wasRacyClean = delta <= racyNanos;
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format(
					"file=%s
					file
					Long.valueOf(lastModified)
					Long.valueOf(racyNanos)));
		}
		return wasRacyClean;
