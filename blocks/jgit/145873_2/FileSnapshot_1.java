		long delta = (read - lastModified) * 1_000_000;
		wasRacyClean = delta <= racyNanos;
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format(
					"read=%d ms
					Long.valueOf(read)
					Long.valueOf(delta)
					Boolean.valueOf(wasRacyClean)));
		}
		return wasRacyClean;
