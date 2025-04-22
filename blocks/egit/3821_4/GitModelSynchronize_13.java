	private static void fireSynchronizeAction(final IWorkbenchWindow window,
			final GitSynchronizeDataSet gsdSet, final ResourceMapping[] mappings) {
		final GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
				gsdSet);

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
				RemoteResourceMappingContext remoteContext = new GitSubscriberResourceMappingContext(subscriber,
						gsdSet);
				SubscriberScopeManager manager = new SubscriberScopeManager(
						subscriber.getName(), mappings, subscriber,
						remoteContext, true);
				GitSubscriberMergeContext context = new GitSubscriberMergeContext(
						subscriber, manager, gsdSet);
				final GitModelSynchronizeParticipant participant = new GitModelSynchronizeParticipant(
						context);

				TeamUI.getSynchronizeManager().addSynchronizeParticipants(
						new ISynchronizeParticipant[] { participant });

				IWorkbenchPart activePart = null;
				if (window != null)
					activePart = window.getActivePage().getActivePart();

				participant.run(activePart);
			}
		});
