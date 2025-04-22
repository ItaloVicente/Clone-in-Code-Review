	/** @return if true received objects are verified to prevent collisions. */
	public boolean isCheckObjectCollisions() {
		return checkObjectCollisions;
	}

	/**
	 * Enable checking for collisions with existing objects.
	 * <p>
	 * By default PackParser looks for each received object in the repository.
	 * If the object already exists, the existing object is compared
	 * byte-for-byte with the newly received copy to ensure they are identical.
	 * The receive is aborted with an exception if any byte differs. This check
	 * is necessary to prevent an evil attacker from supplying a replacement
	 * object into this repository in the event that a discovery enabling SHA-1
	 * collisions is made.
	 * <p>
	 * This check may be very costly to perform, and some repositories may have
	 * other ways to segregate newly received object data. The check is enabled
	 * by default, but can be explicitly disabled if the implementation can
	 * provide the same guarantee, or is willing to accept the risks associated
	 * with bypassing the check.
	 *
	 * @param check
	 *            true to enable collision checking (strongly encouraged).
	 */
	public void setCheckObjectCollisions(boolean check) {
		checkObjectCollisions = check;
	}

