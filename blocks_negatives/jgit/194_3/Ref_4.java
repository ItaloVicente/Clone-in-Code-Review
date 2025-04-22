	public Ref(final Storage st, final String origName, final String refName, final ObjectId id,
			final ObjectId peel, final boolean peeled) {
		storage = st;
		this.origName = origName;
		name = refName;
		objectId = id;
		peeledObjectId = peel;
		this.peeled = peeled;
	}

	/**
	 * Create a new ref pairing.
	 *
	 * @param st
	 *            method used to store this ref.
	 * @param refName
	 *            name of this ref.
	 * @param id
	 *            current value of the ref. May be null to indicate a ref that
	 *            does not exist yet.
	 * @param peel
	 *            peeled value of the ref's tag. May be null if this is not a
	 *            tag or the peeled value is not known.
	 * @param peeled
	 * 			  true if peel represents a the peeled value of the object
	 */
	public Ref(final Storage st, final String refName, final ObjectId id,
			final ObjectId peel, boolean peeled) {
		this(st, refName, refName, id, peel, peeled);
