	/**
	 * Configure this receive pack instance to keep track of the objects assumed
	 * for delta bases.
	 * <p>
	 * By default a receive pack doesn't save the objects that were used as
	 * delta bases. Setting this flag to {@code true} will allow the caller to
	 * use {@link #getBaseObjectIds()} to retrieve that list.
	 *
	 * @param b {@code true} to enable keeping track of delta bases.
	 */
	public void setNeedBaseObjectIds(boolean b) {
		this.needBaseObjectIds = b;
	}

	/**
	 *  @return the set of objects the incoming pack assumed for delta purposes
	 */
	public final Set<ObjectId> getBaseObjectIds() {
		return ip.getBaseObjectIds();
	}

	/**
	 * Configure this receive pack instance to keep track of new objects.
	 * <p>
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

