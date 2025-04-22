	 * @param st
	 *            method used to store this ref.
	 * @param origName
	 *            The name used to resolve this ref
	 * @param refName
	 *            name of this ref.
	 * @param id
	 *            current value of the ref. May be null to indicate a ref that
	 *            does not exist yet.
	 * @param peel
	 *            peeled value of the ref's tag. May be null if this is not a
	 *            tag or not yet peeled (in which case the next parameter should be null)
	 * @param peeled
	 * 			  true if peel represents a the peeled value of the object
	 */
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
