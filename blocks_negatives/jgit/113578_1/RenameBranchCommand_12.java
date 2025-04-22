	/**
	 * @throws RefNotFoundException
	 *             if the old branch can not be found (branch with provided old
	 *             name does not exist or old name resolves to a tag)
	 * @throws InvalidRefNameException
	 *             if the provided new name is <code>null</code> or otherwise
	 *             invalid
	 * @throws RefAlreadyExistsException
	 *             if a branch with the new name already exists
	 * @throws DetachedHeadException
	 *             if rename is tried without specifying the old name and HEAD
	 *             is detached
	 */
