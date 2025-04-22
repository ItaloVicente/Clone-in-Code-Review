			final Transport transport = Transport.open(repository, remote);
			transport.setDryRun(dryRun);
			transport.setTimeout(timeout);
			FetchResult fetchRes = transport.fetch(
					new EclipseGitProgressTransformer(actMonitor), remote
							.getFetchRefSpecs());
			operationResult = new FetchOperationResult(remote.getURIs().get(0),
					fetchRes);
		} catch (final NotSupportedException e) {
			throw new CoreException(Activator.createErrorStatus(e.getMessage(),
					e));
		} catch (final TransportException e) {
			if (actMonitor.isCanceled())
				throw new CoreException(Status.CANCEL_STATUS);
			String errorMessage = NLS
					.bind(
							UIText.FetchConfiguredRemoteAction_TransportErrorDuringFetchMessage,
							e.getMessage());
			operationResult = new FetchOperationResult(remote.getURIs().get(0),
					errorMessage);
