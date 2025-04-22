	/**
	 * Returns whether or not the resource is tracked by Git
	 *
	 * @return whether or not the resource is tracked by Git
	 */
	boolean isTracked();

	/**
	 * Returns whether or not the resource is ignored, either by a global team
	 * ignore in Eclipse, or by .git/info/exclude et al.
	 *
	 * @return whether or not the resource is ignored
	 */
	boolean isIgnored();

	/**
	 * Returns whether or not the resource has changes that are not staged
	 *
	 * @return whether or not the resource is dirty
	 */
	boolean isDirty();

	/**
	 * Returns the staged state of the resource
	 *
	 * The set of allowed values are defined by the <code>Staged</code> enum
	 *
	 * @return the staged state of the resource
	 */
	Staged staged();

	/**
	 * Returns whether or not the resource has merge conflicts
	 *
	 * @return whether or not the resource has merge conflicts
	 */
	boolean hasConflicts();

	/**
	 * Returns whether or not the resource is assumed valid
	 *
	 * @return whether or not the resource is assumed valid
	 */
	boolean isAssumeValid();
