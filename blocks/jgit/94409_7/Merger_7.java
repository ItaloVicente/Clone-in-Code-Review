	protected Merger(ObjectInserter oi) {
		db = null;
		inserter = oi;
		reader = oi.newReader();
		walk = new RevWalk(reader);
	}

