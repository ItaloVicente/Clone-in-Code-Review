					if (monitor.isCanceled()) {
						operationResult.addOperationResult(uri,
								CoreText.PushOperation_resultCancelled);
						continue;
					}

					Collection<RemoteRefUpdate> refUpdates = specification.getRefUpdates(uri);
					final EclipseGitProgressTransformer gitSubMonitor = new EclipseGitProgressTransformer(
							subMonitor);

					try {
						Transport transport = Transport.open(localDb, uri);
						transport.setDryRun(dryRun);
						transport.setTimeout(timeout);
						if (credentialsProvider != null)
							transport.setCredentialsProvider(credentialsProvider);
						PushResult result = transport.push(gitSubMonitor, refUpdates, out);

						operationResult.addOperationResult(result.getURI(), result);
						specification.addURIRefUpdates(result.getURI(), result.getRemoteUpdates());
					} catch (JGitInternalException e) {
						String errorMessage = e.getCause() != null ? e
								.getCause().getMessage() : e.getMessage();
						String userMessage = NLS.bind(
										CoreText.PushOperation_InternalExceptionOccurredMessage,
										errorMessage);
						handleException(uri, e, userMessage);
					} catch (Exception e) {
						handleException(uri, e, e.getMessage());
					}

					monitor.worked(WORK_UNITS_PER_TRANSPORT);
				} finally {
					subMonitor.beginTask("", WORK_UNITS_PER_TRANSPORT); //$NON-NLS-1$
					subMonitor.done();
					subMonitor.done();
