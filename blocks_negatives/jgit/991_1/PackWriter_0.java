	public PackWriter(final Repository repo, final ProgressMonitor monitor) {
		this(repo, monitor, monitor);
	}

	/**
	 * Create writer for specified repository.
	 * <p>
	 * Objects for packing are specified in {@link #preparePack(Iterator)} or
	 * {@link #preparePack(Collection, Collection)}.
	 *
	 * @param repo
	 *            repository where objects are stored.
	 * @param imonitor
	 *            operations progress monitor, used within
	 *            {@link #preparePack(Iterator)},
	 *            {@link #preparePack(Collection, Collection)}
	 * @param wmonitor
	 *            operations progress monitor, used within
	 *            {@link #writePack(OutputStream)}.
	 */
	public PackWriter(final Repository repo, final ProgressMonitor imonitor,
			final ProgressMonitor wmonitor) {
