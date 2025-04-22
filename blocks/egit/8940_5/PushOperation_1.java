						Transport transport = Transport.open(localDb, uri);
						transport.setDryRun(dryRun);
						transport.setTimeout(timeout);
						if (credentialsProvider != null)
							transport.setCredentialsProvider(credentialsProvider);
						PushResult result = transport.push(gitSubMonitor, refUpdates);

						operationResult.addOperationResult(result.getURI(), result);
						specification.addURIRefUpdates(result.getURI(), result.getRemoteUpdates());
