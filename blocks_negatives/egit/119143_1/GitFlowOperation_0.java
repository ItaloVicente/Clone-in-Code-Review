	/**
	 * @param monitor
	 * @return resulting of fetching from remote
	 * @throws URISyntaxException
	 * @throws InvocationTargetException
	 * @deprecated Use {@link GitFlowOperation#fetch(IProgressMonitor, int)}
	 *             instead.
	 */
	@Deprecated
	protected FetchResult fetch(IProgressMonitor monitor)
			throws URISyntaxException, InvocationTargetException {
		return fetch(monitor, 0);
	}

