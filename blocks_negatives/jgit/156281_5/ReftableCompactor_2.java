		bytesToCompact += sz;
		adjustUpdateIndexes(reader);
		tables.addFirst(reader);
		return true;
	}

	private void adjustUpdateIndexes(ReftableReader reader) throws IOException {
		if (minUpdateIndex == -1) {
			minUpdateIndex = reader.minUpdateIndex();
		} else {
			minUpdateIndex = Math.min(minUpdateIndex, reader.minUpdateIndex());
		}
		maxUpdateIndex = Math.max(maxUpdateIndex, reader.maxUpdateIndex());
