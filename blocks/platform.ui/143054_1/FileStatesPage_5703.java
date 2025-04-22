		long days = prefs
				.getDefaultLong(ResourcesPlugin.PREF_FILE_STATE_LONGEVITY)
				/ DAY_LENGTH;
		long megabytes = prefs
				.getDefaultLong(ResourcesPlugin.PREF_MAX_FILE_STATE_SIZE)
				/ MEGABYTES;
		this.longevityText.setText(String.valueOf(days));
