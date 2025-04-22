						Iterable<PushResult> results = git.push().setRemote(
								uri.toPrivateString()).setRefSpecs(specs)
								.setDryRun(dryRun).setTimeout(timeout)
								.setProgressMonitor(gitSubMonitor)
								.setCredentialsProvider(credentialsProvider)
								.call();
						for (PushResult result : results) {
							operationResult.addOperationResult(result.getURI(),
									result);
							specification.addURIRefUpdates(result.getURI(),
									result.getRemoteUpdates());
						}
