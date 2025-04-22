	public boolean tryAddFirst(ReftableReader reader) throws IOException {
		long sz = reader.size();
		if (compactBytesLimit > 0 && bytesToCompact + sz > compactBytesLimit) {
			return false;
		}
		bytesToCompact += sz;
		tables.addFirst(reader);
		return true;
	}

