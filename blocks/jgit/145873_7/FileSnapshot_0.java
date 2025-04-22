		long delta = (read - lastModified) * 1_000_000;
		wasRacyClean = delta <= racyNanos;
		LOG.debug("file={}
				file
				Long.valueOf(lastModified)
				Long.valueOf(racyNanos));
		return wasRacyClean;
