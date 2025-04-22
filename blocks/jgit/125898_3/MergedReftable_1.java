	@Override
	public RefCursor seekPrefix(String prefix) throws IOException {
		MergedRefCursor m = new MergedRefCursor();
		for (int i = 0; i < tables.length; i++) {
			m.add(new RefQueueEntry(tables[i].seekPrefix(prefix)
		}
		return m;
	}

