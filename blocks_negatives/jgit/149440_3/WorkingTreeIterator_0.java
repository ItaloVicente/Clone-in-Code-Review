				|| (fileLastModified.getNano() == 0)) {
			if (fileLastModified.getEpochSecond() != cacheLastModified
					.getEpochSecond()) {
				return MetadataDiff.DIFFER_BY_TIMESTAMP;
			} else if (entry.isSmudged()) {
				return MetadataDiff.SMUDGED;
