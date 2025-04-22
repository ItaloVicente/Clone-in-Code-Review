		if (fileLastModified != cacheLastModified)
			return MetadataDiff.DIFFER_BY_TIMESTAMP;
		else if (!entry.isSmudged())
			return MetadataDiff.EQUAL;
		else
			return MetadataDiff.SMUDGED;
	}

	public boolean isModified(DirCacheEntry entry
		MetadataDiff diff = compareMetadata(entry);
		switch (diff) {
		case DIFFER_BY_TIMESTAMP:
			if (forceContentCheck)
