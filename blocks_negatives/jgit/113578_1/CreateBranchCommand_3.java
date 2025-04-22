	/**
	 * @throws RefAlreadyExistsException
	 *             when trying to create (without force) a branch with a name
	 *             that already exists
	 * @throws RefNotFoundException
	 *             if the start point can not be found
	 * @throws InvalidRefNameException
	 *             if the provided name is <code>null</code> or otherwise
	 *             invalid
	 * @return the newly created branch
	 */
