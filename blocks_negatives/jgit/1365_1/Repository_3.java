	/**
	 * Access a tag by symbolic name.
	 *
	 * @param revstr
	 * @return a Tag or null
	 * @throws IOException on I/O error or unexpected type
	 * @deprecated Use {@link #resolve(String)} and feed its return value to
	 * {@link org.eclipse.jgit.revwalk.RevWalk#parseTag(AnyObjectId)}.
	 */
	@Deprecated
	public Tag mapTag(String revstr) throws IOException {
		final ObjectId id = resolve(revstr);
		return id != null ? mapTag(revstr, id) : null;
	}

	/**
	 * Access a Tag by SHA'1 id
	 * @param refName
	 * @param id
	 * @return Commit or null
	 * @throws IOException for I/O error or unexpected object type.
	 * @deprecated Use {@link org.eclipse.jgit.revwalk.RevWalk#parseTag(AnyObjectId)}.
	 */
	@Deprecated
	public Tag mapTag(final String refName, final ObjectId id) throws IOException {
		final ObjectLoader or;
		try {
			or = open(id);
		} catch (MissingObjectException notFound) {
			return null;
		}
		if (or.getType() == Constants.OBJ_TAG)
			return new Tag(this, id, refName, or.getCachedBytes());
		return new Tag(this, id, refName, null);
	}

