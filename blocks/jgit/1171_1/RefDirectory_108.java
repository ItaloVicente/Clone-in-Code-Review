	private ObjectIdRef doPeel(final Ref leaf) throws MissingObjectException
			IOException {
		RevWalk rw = new RevWalk(getRepository());
		try {
			RevObject obj = rw.parseAny(leaf.getObjectId());
			if (obj instanceof RevTag) {
				return new ObjectIdRef.PeeledTag(leaf.getStorage()
						.getName()
			} else {
				return new ObjectIdRef.PeeledNonTag(leaf.getStorage()
						.getName()
			}
		} finally {
			rw.release();
		}
	}

