		Instant cacheLastModified = entry.getLastModifiedInstant();
		Instant fileLastModified = getEntryLastModifiedInstant();
		if ((getOptions().getCheckStat() == CheckStat.MINIMAL)
				|| (cacheLastModified.getNano() == 0)
				|| (fileLastModified.getNano() == 0)) {
			if (fileLastModified.getEpochSecond() != cacheLastModified
					.getEpochSecond()) {
				return MetadataDiff.DIFFER_BY_TIMESTAMP;
			}
		}
		if (!fileLastModified.equals(cacheLastModified)) {
