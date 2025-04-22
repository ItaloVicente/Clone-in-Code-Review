			}
		else {
			final EclipseGitProgressTransformer gitMonitor = new EclipseGitProgressTransformer(
					monitor);
			try {
				Iterable<PushResult> results = git.push().setRemote(
						rc.getName()).setDryRun(dryRun).setTimeout(timeout)
						.setProgressMonitor(gitMonitor).setCredentialsProvider(
								credentialsProvider).call();
				for (PushResult result : results) {
					operationResult.addOperationResult(result.getURI(), result);
