				if (progress.isCanceled()) {
					operationResult.addOperationResult(uri,
							CoreText.PushOperation_resultCancelled);
					continue;
				}

				Collection<RemoteRefUpdate> refUpdates = specification
						.getRefUpdates(uri);
				final EclipseGitProgressTransformer gitSubMonitor = new EclipseGitProgressTransformer(
						progress.newChild(WORK_UNITS_PER_TRANSPORT));
