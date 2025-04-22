	/**
	 * Clone an operation.
	 *
	 * <p>
	 *   This is used for requeueing operations after a server is found to be
	 *   down.
	 * </p>
	 *
	 * <p>
	 *   Note that it returns more than one operation because a multi-get
	 *   could potentially need to be played against a large number of
	 *   underlying servers.  In this case, there's a separate operation for
	 *   each, and callback fa\u00E7ade to reassemble them.  It is left up to
	 *   the operation pipeline to perform whatever optimization is required
	 *   to turn these back into multi-gets.
	 * </p>
	 *
	 * @param op the operation to clone
	 * @return a new operation for each key in the original operation
	 */
	Collection<Operation> clone(KeyedOperation op);
