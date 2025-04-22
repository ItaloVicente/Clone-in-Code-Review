		boolean launchFetch = Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.SYNC_VIEW_FETCH_BEFORE_LAUNCH);
		Job fetchJob = null;
		if (launchFetch) {
			fetchJob = new SynchronizeFetchJob(gsdSet);
			fetchJob.setUser(true);

			fetchJob.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					IWorkbenchPart activePart = window.getActivePage().getActivePart();
					participant.run(activePart);
				}
			});

			fetchJob.schedule();
		}
