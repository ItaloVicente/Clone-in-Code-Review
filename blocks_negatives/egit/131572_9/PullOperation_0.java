		if (!results.isEmpty())
			throw new CoreException(new Status(IStatus.ERROR, Activator
					.getPluginId(), CoreText.OperationAlreadyExecuted));
		SubMonitor totalProgress = SubMonitor.convert(m,
				NLS.bind(CoreText.PullOperation_TaskName,
						Integer.valueOf(repositories.length)),
				1);
		IWorkspaceRunnable action = new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor mymonitor) throws CoreException {
				if (mymonitor.isCanceled())
					throw new CoreException(Status.CANCEL_STATUS);
				SubMonitor progress = SubMonitor.convert(mymonitor,
						repositories.length * 2);
				for (int i = 0; i < repositories.length; i++) {
					Repository repository = repositories[i];
					IProject[] validProjects = ProjectUtil.getValidOpenProjects(repository);
					PullResult pullResult = null;
					try (Git git = new Git(repository)) {
						PullCommand pull = git.pull();
						SubMonitor newChild = progress.newChild(1,
								SubMonitor.SUPPRESS_NONE);
						pull.setProgressMonitor(new EclipseGitProgressTransformer(
										newChild));
						pull.setTimeout(timeout);
						pull.setCredentialsProvider(credentialsProvider);
						PullReferenceConfig config = configs.get(repository);
						newChild.setTaskName(
								getPullTaskName(repository, config));
						if (config != null) {
							if (config.getRemote() != null) {
								pull.setRemote(config.getRemote());
							}
							if (config.getReference() != null) {
								pull.setRemoteBranchName(config.getReference());
							}
							pull.setRebase(config.getUpstreamConfig());
						}
						MergeStrategy strategy = Activator.getDefault()
								.getPreferredMergeStrategy();
						if (strategy != null) {
							pull.setStrategy(strategy);
						}
						pullResult = pull.call();
						results.put(repository, pullResult);
					} catch (DetachedHeadException e) {
						results.put(repository, Activator.error(
								CoreText.PullOperation_DetachedHeadMessage, e));
					} catch (InvalidConfigurationException e) {
						IStatus error = Activator
								.error(CoreText.PullOperation_PullNotConfiguredMessage,
										e);
						results.put(repository, error);
					} catch (GitAPIException e) {
						results.put(repository,
								Activator.error(e.getMessage(), e));
					} catch (JGitInternalException e) {
						Throwable cause = e.getCause();
						if (cause == null || !(cause instanceof TransportException))
							cause = e;
						results.put(repository,
								Activator.error(cause.getMessage(), cause));
					} finally {
						if (refreshNeeded(pullResult)) {
							ProjectUtil.refreshValidProjects(validProjects,
									progress.newChild(1,
											SubMonitor.SUPPRESS_NONE));
						} else {
							progress.worked(1);
						}
