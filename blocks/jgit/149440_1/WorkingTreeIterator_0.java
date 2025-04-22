		long cacheSeconds = cacheLastModified.getEpochSecond();
		long fileSeconds = fileLastModified.getEpochSecond();
		if (cacheSeconds != fileSeconds) {
			return MetadataDiff.DIFFER_BY_TIMESTAMP;
		}
		int cacheSubSecond = cacheLastModified.getNano();
		int fileSubSecond = fileLastModified.getNano();
