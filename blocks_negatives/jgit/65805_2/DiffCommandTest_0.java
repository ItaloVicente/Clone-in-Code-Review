
	private AbstractTreeIterator getTreeIterator(String name)
			throws IOException {
		final ObjectId id = db.resolve(name);
		if (id == null)
			throw new IllegalArgumentException(name);
		final CanonicalTreeParser p = new CanonicalTreeParser();
		try (ObjectReader or = db.newObjectReader();
				RevWalk rw = new RevWalk(db)) {
			p.reset(or, rw.parseTree(id));
			return p;
		}
	}
