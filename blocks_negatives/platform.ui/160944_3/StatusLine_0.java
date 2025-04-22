		fProgressBar.getDisplay().timerExec(DELAY_PROGRESS, timer);
		if (!animated) {
			fProgressBar.beginTask(totalWork);
		}
		if (name == null) {
			fTaskName = Util.ZERO_LENGTH_STRING;
