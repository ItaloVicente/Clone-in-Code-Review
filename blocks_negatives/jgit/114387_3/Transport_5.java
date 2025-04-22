	 * Convert push remote refs update specification from {@link RefSpec} form
	 * to {@link RemoteRefUpdate}. Conversion expands wildcards by matching
	 * source part to local refs. expectedOldObjectId in RemoteRefUpdate is
	 * set according to leases. Tracking branch is configured if RefSpec destination
	 * matches source of any fetch ref spec for this transport remote
	 * configuration.
