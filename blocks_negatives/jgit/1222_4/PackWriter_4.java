		for (ObjectId id : interestingObjects) {
			RevObject o = walker.parseAny(id);
			walker.markStart(o);
		}
		if (uninterestingObjects != null) {
			for (ObjectId id : uninterestingObjects) {
				final RevObject o;
