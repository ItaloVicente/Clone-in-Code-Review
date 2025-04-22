		this(reader.getOptions().getDeltaBaseCacheLimit());
	}

	DeltaBaseCache(int maxBytes) {
		maxByteCount = maxBytes;
		table = new Entry[1 << TABLE_BITS];
