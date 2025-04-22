	private void scheduleAutoHide() {
		if (!fAutoHideScheduled) {
			fAutoHideScheduled = true;
			int timeUntilNextUpdate = Math
					.max(AUTOHIDE_INACTIVITY_TIMER_MS - (int) (System.currentTimeMillis() - fLastWorkReported), 0);
			getDisplay().timerExec(timeUntilNextUpdate, new Runnable() {
				@Override
				public void run() {
					if (isDisposed()) {
						return;
					}
					fAutoHideScheduled = false;
					long timeSinceLastUpdate = System.currentTimeMillis() - fLastWorkReported;
					if (timeSinceLastUpdate >= AUTOHIDE_INACTIVITY_TIMER_MS) {
						hideProgress();
					} else {
						scheduleAutoHide();
					}
				}
			});
		}
	}

