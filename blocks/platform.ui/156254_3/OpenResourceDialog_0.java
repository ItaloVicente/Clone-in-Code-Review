	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = super.getDialogSettings();
		if (settings.get(FILTER_BY_LOCATION) == null) {
			settings.put(FILTER_BY_LOCATION, true);
		}
		return settings;
	}

