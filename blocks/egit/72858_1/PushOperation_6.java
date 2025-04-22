					progress.worked(WORK_UNITS_PER_TRANSPORT / 2);
				}
			else {
				final EclipseGitProgressTransformer gitMonitor = new EclipseGitProgressTransformer(
						progress.newChild(totalWork));
				try {
					Iterable<PushResult> results = git.push()
							.setRemote(remoteName).setDryRun(dryRun)
							.setTimeout(timeout).setProgressMonitor(gitMonitor)
							.setCredentialsProvider(credentialsProvider)
							.setOutputStream(out).call();
					for (PushResult result : results) {
						operationResult.addOperationResult(result.getURI(),
								result);
					}
				} catch (JGitInternalException e) {
					String errorMessage = e.getCause() != null
							? e.getCause().getMessage() : e.getMessage();
					String userMessage = NLS.bind(
							CoreText.PushOperation_InternalExceptionOccurredMessage,
							errorMessage);
					URIish uri = getPushURIForErrorHandling();
					handleException(uri, e, userMessage);
				} catch (Exception e) {
					URIish uri = getPushURIForErrorHandling();
					handleException(uri, e, e.getMessage());
