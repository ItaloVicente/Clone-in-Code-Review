	 * By default a receive pack doesn't save the new objects that were created
	 * when it was instantiated. Setting this flag to {@code true} allows the
	 * caller to use {@link #getNewObjectIds()} to retrieve that list.
	 *
	 * @param b {@code true} to enable keeping track of new objects.
	 */
	public void setNeedNewObjectIds(boolean b) {
		this.needNewObjectIds = b;
	}

	/** @return the new objects that were sent by the user */
	public final Set<ObjectId> getNewObjectIds() {
		return ip.getNewObjectIds();
	}

	/**
	 * Configure this receive pack instance to ensure that the provided
	 * objects are visible to the user.
