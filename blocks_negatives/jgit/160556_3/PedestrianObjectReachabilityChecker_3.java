	 * Checks that all targets are reachable from the starters.
	 *
	 * @param targets
	 *            objects we want to reach from the starters
	 * @param starters
	 *            objects known to be reachable to the caller
	 * @return Optional with an unreachable target if there is any (there could
	 *         be more than one). Empty optional means all targets are
	 *         reachable.
	 * @throws MissingObjectException
	 *             An object was missing. This should not happen as the caller
	 *             checked this while doing
	 *             {@link RevWalk#parseAny(AnyObjectId)} to convert ObjectIds to
	 *             RevObjects.
	 * @throws IncorrectObjectTypeException
	 *             Incorrect object type. As with missing objects, this should
	 *             not happen if the caller used
	 *             {@link RevWalk#parseAny(AnyObjectId)}.
	 * @throws IOException
	 *             Cannot access underlying storage
