				transport = Transport.open(localDb, uri);
				if (credentialsProvider != null)
					transport.setCredentialsProvider(credentialsProvider);
				transport.setTimeout(this.timeout);

				if (rc != null)
					transport.applyConfig(rc);
				transport.setDryRun(dryRun);
				final EclipseGitProgressTransformer gitSubMonitor = new EclipseGitProgressTransformer(
						subMonitor);
				final PushResult pr = transport.push(gitSubMonitor,
						specification.getRefUpdates(uri));
				operationResult.addOperationResult(uri, pr);
				monitor.worked(WORK_UNITS_PER_TRANSPORT);
			} catch (final NoRemoteRepositoryException e) {
				operationResult.addOperationResult(uri, NLS.bind(
						CoreText.PushOperation_resultNoServiceError, e
								.getMessage()));
			} catch (final TransportException e) {
				operationResult.addOperationResult(uri, NLS.bind(
						CoreText.PushOperation_resultTransportError, e
								.getMessage()));
			} catch (final NotSupportedException e) {
				operationResult.addOperationResult(uri, NLS.bind(
						CoreText.PushOperation_resultNotSupported, e
								.getMessage()));
			} finally {
				if (transport != null) {
					transport.close();
