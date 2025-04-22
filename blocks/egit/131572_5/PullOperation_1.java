		if (!results.isEmpty()) {
			throw new CoreException(
					new Status(IStatus.ERROR, Activator.getPluginId(),
							CoreText.OperationAlreadyExecuted));
		}
		int workers = repositories.length;
		String taskName = NLS.bind(CoreText.PullOperation_TaskName,
				Integer.valueOf(workers));

		int maxThreads = Runtime.getRuntime().availableProcessors();
		JobGroup jobGroup = new JobGroup(taskName, maxThreads, workers);

		SubMonitor progress = SubMonitor.convert(m, workers);
		for (Repository repository : repositories) {
			PullJob pullJob = new PullJob(repository, configs.get(repository));
			pullJob.setJobGroup(jobGroup);
			pullJob.schedule();
		}
		long noTimeouut = 0;
		try {
			jobGroup.join(noTimeouut, progress);
		} catch (OperationCanceledException | InterruptedException e) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
	}

	private final class PullJob extends Job {
		private final Repository repository;
		private final PullReferenceConfig config;

		PullJob(Repository repository, PullReferenceConfig config) {
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
						monitor));
				monitor.newChild(3);
				pull.setTimeout(timeout);
				pull.setCredentialsProvider(credentialsProvider);
				if (config != null) {
					if (config.getRemote() != null) {
						pull.setRemote(config.getRemote());
					}
					if (config.getReference() != null) {
						pull.setRemoteBranchName(config.getReference());
