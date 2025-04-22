		final GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
				set);
		Job initJob = new WorkspaceJob(
				UIText.GitModelSynchonize_fetchGitDataJobName) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				subscriber.init(monitor);
				return Status.OK_STATUS;
			}
		};
		initJob.setUser(true);
		initJob.setRule(RuleUtil.getRuleForRepositories(resources));
		initJob.schedule();
		try {
			initJob.join();
		} catch (InterruptedException e) {
			Activator.logError(UIText.GitModelSynchonize_fetchInterrupted, e);
		}
