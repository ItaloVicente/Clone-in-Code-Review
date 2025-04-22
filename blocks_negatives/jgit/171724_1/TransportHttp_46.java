		return openFetch(Collections.emptyList());
	}

	@Override
	public FetchConnection openFetch(Collection<RefSpec> refSpecs,
			String... additionalPatterns)
			throws NotSupportedException, TransportException {
