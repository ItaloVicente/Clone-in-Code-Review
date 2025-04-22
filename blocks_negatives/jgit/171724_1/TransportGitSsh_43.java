	@Override
	public FetchConnection openFetch(Collection<RefSpec> refSpecs,
			String... additionalPatterns)
			throws NotSupportedException, TransportException {
		return new SshFetchConnection(refSpecs, additionalPatterns);
	}

