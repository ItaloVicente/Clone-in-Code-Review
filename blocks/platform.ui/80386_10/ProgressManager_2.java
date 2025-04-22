		synchronized (pendingUpdatesMutex) {
			pendingJobUpdates.add(info);
		}
		uiRefreshThrottler.throttledExec();
	}

	private void safeAsyncExec(Runnable runnable) {
		if (!display.isDisposed()) {
			display.asyncExec(runnable);
		}
