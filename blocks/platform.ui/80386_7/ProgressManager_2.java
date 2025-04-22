		pendingJobUpdates.add(info);
		refreshJobs.throttledExec();
	}

	private void safeAsyncExec(Runnable runnable) {
		if (!display.isDisposed()) {
			display.asyncExec(runnable);
		}
