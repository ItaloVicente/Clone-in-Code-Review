	private static GitDateFormatter getNewDateFormatter() {
		boolean useRelativeDates = Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_RELATIVE_DATE);
		if (useRelativeDates)
			return new GitDateFormatter(Format.RELATIVE);
		else
			return new GitDateFormatter(Format.LOCALE);
	}

