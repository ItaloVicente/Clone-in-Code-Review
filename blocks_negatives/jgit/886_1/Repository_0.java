	/** @return a new inserter to create objects in {@link #getObjectDatabase()} */
	public ObjectInserter newObjectInserter() {
		return getObjectDatabase().newInserter();
	}

	/** @return the reference database which stores the reference namespace. */
