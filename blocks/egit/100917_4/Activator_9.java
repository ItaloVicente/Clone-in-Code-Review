				progress.worked(1);
			});

			if (GitTraceLocation.REPOSITORYCHANGESCANNER.isActive()) {
				GitTraceLocation.getTrace().trace(
						GitTraceLocation.REPOSITORYCHANGESCANNER.getLocation(),
						"Calculated refresh for repository " + workTree); //$NON-NLS-1$
