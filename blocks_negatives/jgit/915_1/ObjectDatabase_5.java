	protected abstract boolean hasObject1(AnyObjectId objectId);

	/**
	 * Slow half of {@link #hasObject(AnyObjectId)}.
	 *
	 * @param objectName
	 *            identity of the object to test for existence of.
	 * @return true if the specified object is stored in this database.
	 */
	protected boolean hasObject2(String objectName) {
		return false;
	}
