	private ObjectWalk setUpWalker(
			final Collection<? extends ObjectId> interestingObjects
			final Collection<? extends ObjectId> uninterestingObjects
			final Collection<? extends ObjectId> unshallowObjects
			int depth)
			throws MissingObjectException
			IncorrectObjectTypeException {
		final DepthWalk.ObjectWalk walker = new DepthWalk.ObjectWalk(reader
				depth);
		walker.setRetainBody(false);

		for (ObjectId id : interestingObjects) {
			RevObject o = walker.parseAny(id);
			walker.markRoot(o);
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
				final RevObject o = walker.parseAny(id);
				walker.markUnshallow(o);
			}
		}
		return walker;
	}

