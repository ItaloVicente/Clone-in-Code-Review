	/**
	 * Adds a new conflicting path
	 * @param conflictingPath
	 * @return {@code this}
	 */
	CheckoutConflictException addConflictingPath(String conflictingPath) {
		if (conflictingPaths == null)
			conflictingPaths = new LinkedList<String>();
		conflictingPaths.add(conflictingPath);
		return this;
