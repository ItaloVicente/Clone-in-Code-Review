	public ObjectId resolve(final String revstr)
			throws AmbiguousObjectException, IOException {
		RevWalk rw = new RevWalk(this);
		try {
			Object resolved = resolve(rw, revstr);
			if (resolved instanceof String) {
				return getRef((String) resolved).getLeaf().getObjectId();
			} else {
				return (ObjectId) resolved;
			}
		} finally {
			rw.release();
		}
	}
