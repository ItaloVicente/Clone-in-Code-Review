	private final class PullJob extends Job {
		private final Repository repository;
		private final PullReferenceConfig config;

		private PullJob(Repository repository,
				PullReferenceConfig config) {
			super(getPullTaskName(repository, config));
			this.repository = repository;
			this.config = config;
			setRule(RuleUtil.getRule(repository));
		}

		@Override
		public IStatus run(IProgressMonitor mymonitor) {
			PullResult pullResult = null;
			try (Git git = new Git(repository)) {
				PullCommand pull = git.pull();
				SubMonitor monitor = SubMonitor.convert(mymonitor, 4);
				pull.setProgressMonitor(new EclipseGitProgressTransformer(
						mymonitor));
				pull.setTimeout(timeout);
				pull.setCredentialsProvider(credentialsProvider);
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
				monitor.worked(3);
				synchronized (results) {
					results.put(repository, pullResult);
				}
				IProject[] projects = ProjectUtil
						.getValidOpenProjects(repository);
				ProjectUtil.refreshValidProjects(projects, mymonitor);
				monitor.worked(1);
				return Status.OK_STATUS;
			} catch (DetachedHeadException e) {
				return Activator.error(
						CoreText.PullOperation_DetachedHeadMessage, e);
			} catch (InvalidConfigurationException e) {
				return Activator
						.error(CoreText.PullOperation_PullNotConfiguredMessage,
								e);
			} catch (GitAPIException | CoreException e) {
				return Activator.error(e.getMessage(), e);
			} catch (JGitInternalException e) {
				Throwable cause = e.getCause();
				if (cause == null || !(cause instanceof TransportException))
					cause = e;
				return Activator.error(cause.getMessage(), cause);
			}
		}

		@Override
		public boolean belongsTo(Object family) {
			return PullOperation.class.getSimpleName()
					.equals(family);
		}
	}

