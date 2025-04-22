				progress.worked(WORK_UNITS_PER_TRANSPORT / 2);
			}
		else {
			final EclipseGitProgressTransformer gitMonitor = new EclipseGitProgressTransformer(
					progress.newChild(totalWork));
			try {
				Iterable<PushResult> results = git.push().setRemote(remoteName)
						.setDryRun(dryRun).setTimeout(timeout)
						.setProgressMonitor(gitMonitor)
						.setCredentialsProvider(credentialsProvider)
						.setOutputStream(out).call();
				for (PushResult result : results) {
					operationResult.addOperationResult(result.getURI(), result);
