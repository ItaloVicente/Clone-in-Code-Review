	@Override
	public void run(final IWorkbenchPart part) {
 		boolean launchFetch = Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.SYNC_VIEW_FETCH_BEFORE_LAUNCH);
		if (launchFetch || gsds.forceFetch()) {
			Job fetchJob = new SynchronizeFetchJob(gsds);
			fetchJob.setUser(true);
			fetchJob.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					GitModelSynchronizeParticipant.super.run(part);
				}
			});

			fetchJob.schedule();
		} else
			super.run(part);
	}

