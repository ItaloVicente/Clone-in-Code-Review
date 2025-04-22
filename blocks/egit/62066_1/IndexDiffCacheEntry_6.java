		}
		listeners.clear();
		if (reloadJob != null) {
			reloadJob.cancel();
			reloadJob = null;
		}
		if (updateJob != null) {
			updateJob.cleanupAndCancel();
			updateJob = null;
		}
