	private ObjectWalk setUpWalker(
			final Collection<? extends ObjectId> interestingObjects
			final Collection<? extends ObjectId> uninterestingObjects
			final Collection<? extends ObjectId> unshallowObjects
			int depth)
			throws MissingObjectException
			IncorrectObjectTypeException {
		final ObjectWalk walker;
		walker = new DepthWalk.ObjectWalk(reader
		walker.setRetainBody(false);
		walker.sort(RevSort.COMMIT_TIME_DESC);
		if (thin)
			walker.sort(RevSort.BOUNDARY

		for (ObjectId id : interestingObjects) {
			RevObject o = walker.parseAny(id);
			walker.markStart(o);
		}
		if (uninterestingObjects != null) {
			for (ObjectId id : uninterestingObjects) {
				final RevObject o;
				try {
					o = walker.parseAny(id);
				} catch (MissingObjectException x) {
					if (ignoreMissingUninteresting)
						continue;
					throw x;
				}
				walker.markUninteresting(o);
			}
		}
		if (unshallowObjects != null) {
			for (ObjectId id : unshallowObjects) {
				final RevObject o;
				try {
					o = walker.parseAny(id);
				} catch (MissingObjectException x) {
					if (ignoreMissingUninteresting)
						continue;
					throw x;
				}
				((DepthWalk.ObjectWalk)walker).markUnshallow(o);
			}
		}
		return walker;
	}

