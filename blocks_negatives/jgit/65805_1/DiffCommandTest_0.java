
	private AbstractTreeIterator getTreeIterator(String name)
			throws IOException {
		final ObjectId id = db.resolve(name);
		if (id == null)
			throw new IllegalArgumentException(name);
		final CanonicalTreeParser p = new CanonicalTreeParser();
		try (ObjectReader or = db.newObjectReader()) {
			p.reset(or, new RevWalk(db).parseTree(id));
			return p;
		}
	}
