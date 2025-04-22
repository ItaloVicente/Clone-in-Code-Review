		/**
		 * Adds the id and the existing bitmap for the id, if one exists, to the
		 * bitmap.
		 *
		 * @param objectId
		 *            the object ID
		 * @param type
		 *            the Git object type. See {@link Constants}.
		 * @return true if the value was not contained or able to be loaded.
		 * @deprecated use {@link #or} or {@link #addObject} instead.
		 */
		@Deprecated
		boolean add(AnyObjectId objectId, int type);
