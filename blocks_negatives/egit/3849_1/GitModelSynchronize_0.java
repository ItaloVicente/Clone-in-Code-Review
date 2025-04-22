		boolean launchFetch = Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.SYNC_VIEW_FETCH_BEFORE_LAUNCH);
		Job fetchJob = null;
		if (launchFetch) {
			fetchJob = new SynchronizeFetchJob(gsdSet);
			fetchJob.setUser(true);
			fetchJob.schedule();
		}

