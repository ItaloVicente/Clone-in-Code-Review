	/**
	 * Create a commit associated with these parents and associate it with a
	 * repository.
	 *
	 * @param db
	 *            The repository to which this commit object belongs
	 * @param parentIds
	 *            Id's of the parent(s)
	 */
	public Commit(final Repository db, final ObjectId[] parentIds) {
		objdb = db;
		this.parentIds = parentIds;
	}

	/**
	 * Create a commit object with the specified id and data from and existing
	 * commit object in a repository.
	 *
	 * @param db
	 *            The repository to which this commit object belongs
	 * @param id
	 *            Commit id
	 * @param raw
	 *            Raw commit object data
	 */
	public Commit(final Repository db, final ObjectId id, final byte[] raw) {
		objdb = db;
		commitId = id;
		treeId = ObjectId.fromString(raw, 5);
		parentIds = new ObjectId[1];
		int np=0;
		int rawPtr = 46;
		for (;;) {
			if (raw[rawPtr] != 'p')
				break;
			if (np == 0) {
				parentIds[np++] = ObjectId.fromString(raw, rawPtr + 7);
			} else if (np == 1) {
				parentIds = new ObjectId[] { parentIds[0], ObjectId.fromString(raw, rawPtr + 7) };
				np++;
			} else {
				if (parentIds.length <= np) {
					ObjectId[] old = parentIds;
					parentIds = new ObjectId[parentIds.length+32];
					for (int i=0; i<np; ++i)
						parentIds[i] = old[i];
				}
				parentIds[np++] = ObjectId.fromString(raw, rawPtr + 7);
			}
			rawPtr += 48;
		}
		if (np != parentIds.length) {
			ObjectId[] old = parentIds;
			parentIds = new ObjectId[np];
			for (int i=0; i<np; ++i)
				parentIds[i] = old[i];
		} else
			if (np == 0)
				parentIds = EMPTY_OBJECTID_LIST;
		this.raw = raw;
	}

	/**
	 * @return get repository for the commit
	 */
	public Repository getRepository() {
		return objdb;
	}

	/**
	 * @return The commit object id
	 */
