
	public static PushOperationSpecification create(Repository repository,
			RemoteConfig config, Collection<RefSpec> refSpecs)
			throws IOException {
		PushOperationSpecification result = new PushOperationSpecification();
		Collection<RemoteRefUpdate> remoteRefUpdates = Transport
				.findRemoteRefUpdatesFor(repository, refSpecs,
						config.getFetchRefSpecs());
		boolean added = false;
		for (URIish uri : config.getPushURIs()) {
			result.addURIRefUpdates(uri, copyUpdates(remoteRefUpdates, false));
			added = true;
		}
		if (!added && !config.getURIs().isEmpty()) {
			result.addURIRefUpdates(config.getURIs().get(0), remoteRefUpdates);
		}
		return result;
	}

	public static Collection<RemoteRefUpdate> copyUpdates(
			Collection<RemoteRefUpdate> refUpdates, boolean withExpectedOid)
			throws IOException {
		Collection<RemoteRefUpdate> copy = new ArrayList<>(refUpdates.size());
		for (RemoteRefUpdate rru : refUpdates) {
			copy.add(new RemoteRefUpdate(rru,
					withExpectedOid ? rru.getExpectedOldObjectId() : null));
		}
		return copy;
	}

