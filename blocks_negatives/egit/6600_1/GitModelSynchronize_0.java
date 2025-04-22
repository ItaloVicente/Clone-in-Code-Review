		boolean launchFetch = Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.SYNC_VIEW_FETCH_BEFORE_LAUNCH);
		if (launchFetch || gsdSet.forceFetch()) {
			Job fetchJob = new SynchronizeFetchJob(gsdSet);
			fetchJob.setUser(true);
			fetchJob.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					fireSynchronizeAction(window, gsdSet, mappings);
				}
			});

			fetchJob.schedule();
		} else {
			fireSynchronizeAction(window, gsdSet, mappings);
		}
