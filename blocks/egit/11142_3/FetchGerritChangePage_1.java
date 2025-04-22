	private void storeRunInBackgroundSelection() {
		settings.put(RUN_IN_BACKGROUND, runInBackgroud.getSelection());
	}

	private void restoreRunInBackgroundSelection() {
		runInBackgroud.setSelection(settings.getBoolean(RUN_IN_BACKGROUND));
	}

