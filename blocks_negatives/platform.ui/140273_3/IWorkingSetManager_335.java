    /**
	 * Create a working set that is the union of a collection of other working
	 * sets. One connected (via
	 * {@link IWorkingSetManager#addWorkingSet(IWorkingSet)} this working set
	 * will be automatically updated to reflect the contents of the component
	 * sets, should they themselves change.
	 *
	 * @param name
	 *            the name of the new working set. Should not have leading or
	 *            trailing whitespace.
	 * @param label
	 *            the user-friendly label the working set
	 * @param components
	 *            the component working sets
