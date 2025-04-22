			for (URIish uri : urisToPush) {
				try {
					Collection<RemoteRefUpdate> remoteRefUpdates = Transport
							.findRemoteRefUpdatesFor(repository, pushRefSpecs,
									config.getFetchRefSpecs());
					spec.addURIRefUpdates(uri, remoteRefUpdates);
				} catch (NotSupportedException e) {
					throw new CoreException(Activator.createErrorStatus(
							e.getMessage(), e));
				} catch (IOException e) {
					throw new CoreException(Activator.createErrorStatus(
							e.getMessage(), e));
				}
