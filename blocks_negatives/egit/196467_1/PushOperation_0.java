						PushResult result = transport.push(gitSubMonitor,
								refUpdates, out);

						operationResult.addOperationResult(result.getURI(),
								result);
						specification.addURIRefUpdates(result.getURI(),
								result.getRemoteUpdates());
