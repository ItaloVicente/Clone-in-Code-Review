	/**
	 * Begins a new connection for fetching from the remote repository.
	 * <p>
	 * If the transport has no local repository, the fetch connection can only
	 * be used for reading remote refs.
	 * </p>
	 * <p>
	 * If the server supports git protocol V2, the {@link RefSpec}s and the
	 * additional patterns, if any, are used to restrict the server's ref
	 * advertisement to matching refs only.
	 * </p>
	 * <p>
	 * Transports that want to support git protocol V2 <em>must</em> override
	 * this; the default implementation ignores its arguments and calls
	 * {@link #openFetch()}.
	 * </p>
	 *
	 * @param refSpecs
	 *            that will be fetched via
	 *            {@link FetchConnection#fetch(ProgressMonitor, Collection, java.util.Set, OutputStream)} later
	 * @param additionalPatterns
	 *            that will be set as ref prefixes if the server supports git
	 *            protocol V2; {@code null} values are ignored
	 *
	 * @return a fresh connection to fetch from the remote repository.
	 * @throws org.eclipse.jgit.errors.NotSupportedException
	 *             the implementation does not support fetching.
	 * @throws org.eclipse.jgit.errors.TransportException
	 *             the remote connection could not be established.
	 * @since 5.10
	 */
	public FetchConnection openFetch(Collection<RefSpec> refSpecs,
			String... additionalPatterns)
			throws NotSupportedException, TransportException {
		return openFetch();
	}

