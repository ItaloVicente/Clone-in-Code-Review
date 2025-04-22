	/** A collection of object ids. */
	public interface ObjectIdSet {
		/**
		 * Returns true if the objectId is contained within the collection.
		 *
		 * @param objectId
		 *            the objectId to find
		 * @return whether the collection contains the objectId.
		 */
		boolean contains(AnyObjectId objectId);
	}
