	/**
	 * Flag indicating whether or not the resource is tracked
	 */
	protected boolean tracked = false;

	/**
	 * Flag indicating whether or not the resource is ignored
	 */
	protected boolean ignored = false;

	/**
	 * Flag indicating whether or not the resource has changes that are not
	 * staged
	 */
	protected boolean dirty = false;

	/**
	 * Staged state of the resource
	 */
	protected Staged staged = Staged.NOT_STAGED;

	/**
	 * Flag indicating whether or not the resource has merge conflicts
	 */
	protected boolean conflicts = false;

	/**
	 * Flag indicating whether or not the resource is assumed valid
	 */
	protected boolean assumeValid = false;
