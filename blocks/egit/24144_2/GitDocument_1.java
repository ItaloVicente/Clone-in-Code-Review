		};
		reloadJob.schedule();
	}

	private void cancelReloadJob() {
		if (reloadJob != null && reloadJob.getState() != Job.NONE)
			reloadJob.cancel();
