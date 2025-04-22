	private Tree mapTree(Repository db, String name) throws IOException {
		ObjectId id = db.resolve(name + "^{tree}"); //$NON-NLS-1$
		return id == null ? null : new Tree(db, id, db.open(id)
				.getCachedBytes());
	}

