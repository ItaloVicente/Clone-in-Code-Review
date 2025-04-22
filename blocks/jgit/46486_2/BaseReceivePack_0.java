	private void checkObjectIsReachable(final RevObject obj

		if (!db.hasObject(obj))
			throw new MissingObjectException(obj

		if (walk.lookupOrNull(obj) != null)
			return;

		for (;;) {
			RevObject o;
			while ((o = walk.nextObject()) != null) {
				if (AnyObjectId.equals(o
					return;
			}
			RevCommit c = walk.next();
			if (c == null)
				break;
			if (AnyObjectId.equals(c
				return;
		}

		throw new MissingObjectException(obj
	}

