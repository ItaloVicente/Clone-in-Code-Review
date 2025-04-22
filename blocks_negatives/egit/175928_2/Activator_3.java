
		getPreferenceStore().removePropertyChangeListener(rcs);
		rcs.setReschedule(false);
		rcs.cancel();
		refreshJob.cancel();

		rcs.join();
		refreshJob.join();
		if (GitTraceLocation.REPOSITORYCHANGESCANNER.isActive()) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.REPOSITORYCHANGESCANNER.getLocation(),
		}

