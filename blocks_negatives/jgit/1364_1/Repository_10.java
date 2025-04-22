	/**
	 * Access a Commit object using a symbolic reference. This reference may
	 * be a SHA-1 or ref in combination with a number of symbols translating
	 * from one ref or SHA1-1 to another, such as HEAD^ etc.
	 *
	 * @param revstr a reference to a git commit object
	 * @return a Commit named by the specified string
	 * @throws IOException for I/O error or unexpected object type.
	 *
	 * @see #resolve(String)
	 * @deprecated Use {@link #resolve(String)} and pass its return value to
	 * {@link org.eclipse.jgit.revwalk.RevWalk#parseCommit(AnyObjectId)}.
	 */
	@Deprecated
	public Commit mapCommit(final String revstr) throws IOException {
		final ObjectId id = resolve(revstr);
		return id != null ? mapCommit(id) : null;
	}

	/**
	 * Access any type of Git object by id and
	 *
	 * @param id
	 *            SHA-1 of object to read
	 * @param refName optional, only relevant for simple tags
	 * @return The Git object if found or null
	 * @throws IOException
	 * @deprecated Use {@link org.eclipse.jgit.revwalk.RevWalk#parseCommit(AnyObjectId)},
	 *  or {@link org.eclipse.jgit.revwalk.RevWalk#parseTag(AnyObjectId)}.
	 *  To read a tree, use {@link org.eclipse.jgit.treewalk.TreeWalk#addTree(AnyObjectId)}.
	 *  To read a blob, open it with {@link #open(AnyObjectId)}.
	 */
	@Deprecated
	public Object mapObject(final ObjectId id, final String refName) throws IOException {
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
			return new Commit(this, id, raw);

		case Constants.OBJ_TAG:
			return new Tag(this, id, refName, raw);

		case Constants.OBJ_BLOB:
			return raw;

		default:
			throw new IncorrectObjectTypeException(id,
				JGitText.get().incorrectObjectType_COMMITnorTREEnorBLOBnorTAG);
		}
	}

	/**
	 * Access a Commit by SHA'1 id.
	 * @param id
	 * @return Commit or null
	 * @throws IOException for I/O error or unexpected object type.
	 * @deprecated Use {@link org.eclipse.jgit.revwalk.RevWalk#parseCommit(AnyObjectId)}.
	 */
	@Deprecated
	public Commit mapCommit(final ObjectId id) throws IOException {
		final ObjectLoader or;
		try {
			or = open(id, Constants.OBJ_COMMIT);
		} catch (MissingObjectException notFound) {
			return null;
		}
		return new Commit(this, id, or.getCachedBytes());
	}

