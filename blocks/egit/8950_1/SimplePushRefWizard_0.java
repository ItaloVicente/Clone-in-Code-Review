			RemoteConfig remoteConfig = remote.getConfig();
			List<RefSpec> fetchSpecs = remoteConfig != null ? remoteConfig.getFetchRefSpecs() : null;

			Collection<RemoteRefUpdate> remoteRefUpdates = Transport
					.findRemoteRefUpdatesFor(repo,
							Collections.singleton(refSpec), fetchSpecs);

			specification.addURIRefUpdates(remote.getURI(true), remoteRefUpdates);
