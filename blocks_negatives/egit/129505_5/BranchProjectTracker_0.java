	/**
	 * Snapshot the projects currently associated with the repository
	 * <p>
	 * The memento returned can be later passed to {@link #save(IMemento)} to
	 * persist it
	 *
	 * @see #save(IMemento)
	 * @return memento, will be null on failures
	 */
	public IMemento snapshot() {
