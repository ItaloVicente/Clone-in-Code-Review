	public void run(final Shell shell, boolean dryRun) {
		RemoteConfig config;
		PushOperationSpecification spec;
		Exception pushException = null;
		final PushOperation op;
		try {
			config = new RemoteConfig(repository.getConfig(), remoteName);
			List<URIish> pushURIs = new ArrayList<URIish>();
			pushURIs.addAll(config.getPushURIs());
			if (pushURIs.isEmpty() && !config.getURIs().isEmpty())
				pushURIs.add(config.getURIs().get(0));
			if (pushURIs.isEmpty()) {
				throw new IOException(NLS.bind(
						UIText.PushConfiguredRemoteAction_NoUrisMessage,
						remoteName));
			}
			final Collection<RefSpec> pushSpecs = config.getPushRefSpecs();
			if (pushSpecs.isEmpty()) {
				throw new IOException(NLS.bind(
						UIText.PushConfiguredRemoteAction_NoSpecDefined,
						remoteName));
			}
			final Collection<RemoteRefUpdate> updates = Transport
					.findRemoteRefUpdatesFor(repository, pushSpecs, null);
			if (updates.isEmpty()) {
				throw new IOException(
						NLS.bind(
								UIText.PushConfiguredRemoteAction_NoUpdatesFoundMessage,
								remoteName));
			}
