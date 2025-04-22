	/**
	 * Access a Tree object using a symbolic reference. This reference may
	 * be a SHA-1 or ref in combination with a number of symbols translating
	 * from one ref or SHA1-1 to another, such as HEAD^{tree} etc.
	 *
	 * @param revstr a reference to a git commit object
	 * @return a Tree named by the specified string
	 * @throws IOException
	 *
	 * @see #resolve(String)
	 * @deprecated Use {@link #resolve(String)} and pass its return value to
	 * {@link org.eclipse.jgit.treewalk.TreeWalk#addTree(AnyObjectId)}.
	 */
	@Deprecated
	public Tree mapTree(final String revstr) throws IOException {
		final ObjectId id = resolve(revstr);
		return id != null ? mapTree(id) : null;
	}

	/**
	 * Access a Tree by SHA'1 id.
	 * @param id
	 * @return Tree or null
	 * @throws IOException for I/O error or unexpected object type.
	 * @deprecated Use {@link org.eclipse.jgit.treewalk.TreeWalk#addTree(AnyObjectId)}.
	 */
	@Deprecated
	public Tree mapTree(final ObjectId id) throws IOException {
		final ObjectLoader or;
		try {
			or = open(id);
		} catch (MissingObjectException notFound) {
			return null;
		}
		final byte[] raw = or.getCachedBytes();
		switch (or.getType()) {
		case Constants.OBJ_TREE:
			return new Tree(this, id, raw);

		case Constants.OBJ_COMMIT:
			return mapTree(ObjectId.fromString(raw, 5));

		default:
			throw new IncorrectObjectTypeException(id, Constants.TYPE_TREE);
		}
	}

