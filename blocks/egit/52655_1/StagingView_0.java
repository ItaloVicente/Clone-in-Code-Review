	private void saveSashFormWeightsOnDisposal(final SashForm sashForm,
			final String settingsKey) {
		sashForm.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				getDialogSettings().put(settingsKey,
						intArrayToString(sashForm.getWeights()));
			}
		});
	}

	private IDialogSettings getDialogSettings() {
		return DialogSettings.getOrCreateSection(
				Activator.getDefault().getDialogSettings(),
				StagingView.class.getName());
	}

	private static String intArrayToString(int[] ints) {
		StringBuilder res = new StringBuilder();
		if (ints != null && ints.length > 0) {
			res.append(String.valueOf(ints[0]));
			for (int i = 1; i < ints.length; i++) {
				res.append(',');
				res.append(String.valueOf(ints[i]));
			}
