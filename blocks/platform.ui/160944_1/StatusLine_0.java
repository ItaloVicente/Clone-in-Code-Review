		inUIThread(() -> {
			if (!animated) {
				fProgressBar.beginTask(totalWork);
			}
			if (name == null) {
				fTaskName = Util.ZERO_LENGTH_STRING;
			} else {
				fTaskName = name;
			}
			setMessage(fTaskName);
		});
	}

	private void inUIThread(Runnable r) {
		if (Display.getCurrent() == getDisplay()) {
			r.run();
