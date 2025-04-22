	private RefCursor seekSingleRef(String name) throws IOException {
		MergedRefCursor m = new MergedRefCursor();
		for (int i = tables.length - 1; i >= 0 && m.queue.isEmpty(); i--) {
			m.add(new RefQueueEntry(tables[i].seekRef(name), i));
		}
		return m;
	}

