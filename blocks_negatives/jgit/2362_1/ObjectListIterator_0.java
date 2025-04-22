	private final ObjectWalk walk;

	/**
	 * Initialize the list iterator.
	 *
	 * @param walk
	 *            the revision pool the iterator will use when allocating the
	 *            returned objects.
	 */
	protected ObjectListIterator(ObjectWalk walk) {
		this.walk = walk;
	}

	/**
	 * Lookup an object from the revision pool.
	 *
	 * @param id
	 *            the object to allocate.
	 * @param type
	 *            the type of the object. The type must be accurate, as it is
	 *            used to allocate the proper RevObject instance.
	 * @return the object.
	 */
	protected RevObject lookupAny(AnyObjectId id, int type) {
		return walk.lookupAny(id, type);
	}

