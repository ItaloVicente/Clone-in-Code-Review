			final Collection<RemoteRefUpdate> updates = Transport
					.findRemoteRefUpdatesFor(repository, pushSpecs, null);
			if (updates.isEmpty()) {
				throw new IOException(
						NLS.bind(
								UIText.PushConfiguredRemoteAction_NoUpdatesFoundMessage,
								remoteName));
			}

			spec = new PushOperationSpecification();
			for (final URIish uri : pushURIs)
				spec.addURIRefUpdates(uri,
						ConfirmationPage.copyUpdates(updates));
