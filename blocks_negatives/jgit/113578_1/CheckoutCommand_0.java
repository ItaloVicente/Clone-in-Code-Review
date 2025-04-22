	/**
	 * @throws RefAlreadyExistsException
	 *             when trying to create (without force) a branch with a name
	 *             that already exists
	 * @throws RefNotFoundException
	 *             if the start point or branch can not be found
	 * @throws InvalidRefNameException
	 *             if the provided name is <code>null</code> or otherwise
	 *             invalid
	 * @throws CheckoutConflictException
	 *             if the checkout results in a conflict
	 * @return the newly created branch
	 */
