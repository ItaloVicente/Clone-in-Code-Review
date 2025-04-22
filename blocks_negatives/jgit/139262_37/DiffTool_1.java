	private ObjectStream getObjectStream(Pair pair, Side side, DiffEntry ent) {
		ObjectStream stream = null;
		if (!pair.isWorkingTreeSource(side)) {
			try {
				stream = pair.open(side, ent).openStream();
			} catch (Exception e) {
				stream = null;
