						PullCommand pull = git.pull();
						SubMonitor newChild = progress.newChild(1,
								SubMonitor.SUPPRESS_NONE);
						pull.setProgressMonitor(new EclipseGitProgressTransformer(
										newChild));
						pull.setTimeout(timeout);
						pull.setCredentialsProvider(credentialsProvider);
