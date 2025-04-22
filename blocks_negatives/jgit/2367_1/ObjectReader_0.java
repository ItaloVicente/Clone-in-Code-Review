	/**
	 * Obtain the available pre-computed object reachability lists.
	 * <p>
	 * The lists are indexed by commit ObjectId, so the returned set contains
	 * the commit ObjectIds naming each set.
	 *
	 * @return set of commit ObjectIds that identify lists.
	 */
	public Set<ObjectId> getAvailableObjectLists() {
		return Collections.emptySet();
	}

	/**
	 * Open a pre-computed object list for reading.
	 *
	 * @param listName
	 *            a commit ObjectId previously returned by
	 *            {@link #getAvailableObjectLists()}.
	 * @param walker
	 *            the revision pool to use when looking up objects.
	 * @return the list iterator.
	 * @throws IOException
	 *             the reader cannot load the precomputed list.
	 */
	public ObjectListIterator openObjectList(AnyObjectId listName,
			ObjectWalk walker) throws IOException {
		throw new UnsupportedOperationException();
	}

