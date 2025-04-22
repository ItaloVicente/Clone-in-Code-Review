			final GitResourceVariantTreeSubscriber subscriber,
			final GitModelSynchronizeParticipant participant,
			final IWorkbenchWindow window) {
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
				IWorkbenchPart activePart = null;
				if (window != null)
					activePart = window.getActivePage().getActivePart();

				participant.run(activePart);
			}
		});
