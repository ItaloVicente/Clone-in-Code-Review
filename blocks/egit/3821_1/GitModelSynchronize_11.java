
		if (launchFetch && fetchJob != null)
			try {
				fetchJob.join();
			} catch (InterruptedException e) {
				Activator.logError("Fetch operation interupted", e); //$NON-NLS-1$
			}

		Job syncJob = new Job(UIText.GitModelSynchonize_fetchGitDataJobName) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				subscriber.init(monitor);

				return Status.OK_STATUS;
			}
		};

		syncJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				runParticipant(gsdSet, resources, subscriber);
			}
		});
		syncJob.setUser(true);
		syncJob.schedule();
	}

	private static void runParticipant(final GitSynchronizeDataSet gsdSet,
			IResource[] resources, GitResourceVariantTreeSubscriber subscriber) {
		ResourceMapping[] mappings = getSelectedResourceMappings(resources);
