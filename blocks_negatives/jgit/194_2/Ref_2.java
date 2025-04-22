	private ObjectId objectId;

	private ObjectId peeledObjectId;

	private final String origName;

	private final boolean peeled;

	/**
	 * Create a new ref pairing.
	 *
	 * @param st
	 *            method used to store this ref.
	 * @param origName
	 *            The name used to resolve this ref
	 * @param refName
	 *            name of this ref.
	 * @param id
	 *            current value of the ref. May be null to indicate a ref that
	 *            does not exist yet.
	 */
	public Ref(final Storage st, final String origName, final String refName, final ObjectId id) {
		this(st, origName, refName, id, null, false);
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
	 */
	public Ref(final Storage st, final String refName, final ObjectId id) {
		this(st, refName, refName, id, null, false);
	}

