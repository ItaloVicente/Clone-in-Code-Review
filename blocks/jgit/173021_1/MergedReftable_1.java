	@Override
	public RefCursor seekPastRef(String refName) throws IOException {
		MergedRefCursor m = new MergedRefCursor();
		for (int i = 0; i < tables.length; i++) {
			m.add(new RefQueueEntry(tables[i].seekPastRef(refName)
		}
		return m;
	}

