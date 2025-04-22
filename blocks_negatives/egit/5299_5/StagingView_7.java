
		removeListeners();
		if (reloadJob != null) {
			reloadJob.cancel();
			reloadJob = null;
		}
