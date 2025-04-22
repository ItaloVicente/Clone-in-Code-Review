		Git git = new Git(localDb);

		if (specification != null)
			for (final URIish uri : specification.getURIs()) {
				final SubProgressMonitor subMonitor = new SubProgressMonitor(
						monitor, WORK_UNITS_PER_TRANSPORT,
						SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK);

				try {
					List<RefSpec> specs = new ArrayList<RefSpec>(3);
					for (RemoteRefUpdate update : specification
							.getRefUpdates(uri)) {
						RefSpec spec = new RefSpec();
						spec = spec.setSourceDestination(update.getSrcRef(),
								update.getRemoteName());
						spec = spec.setForceUpdate(update.isForceUpdate());
						specs.add(spec);
					}
					if (monitor.isCanceled()) {
						operationResult.addOperationResult(uri,
								CoreText.PushOperation_resultCancelled);
						continue;
					}

					final EclipseGitProgressTransformer gitSubMonitor = new EclipseGitProgressTransformer(
							subMonitor);

					try {
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
					} catch (JGitInternalException e) {
						String errorMessage = e.getCause() != null ? e
								.getCause().getMessage() : e.getMessage();
						String userMessage = NLS
								.bind(
										CoreText.PushOperation_InternalExceptionOccuredMessage,
										errorMessage);
						operationResult.addOperationResult(uri, userMessage);
					} catch (InvalidRemoteException e) {
						operationResult.addOperationResult(uri, e.getMessage());
					}

					monitor.worked(WORK_UNITS_PER_TRANSPORT);
				} finally {
					subMonitor.beginTask("", WORK_UNITS_PER_TRANSPORT); //$NON-NLS-1$
					subMonitor.done();
					subMonitor.done();
