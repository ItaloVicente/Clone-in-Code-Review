		cancelCheck++;
		if (cancelCheck > CANCEL_CHECK_PERIOD) {
			canceled = monitor.isCanceled();
			cancelCheck = 0;
		}
		return canceled;
	}
