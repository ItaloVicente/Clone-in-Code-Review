
		final RawTextComparator textComparator;
		if (Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.BLAME_IGNORE_WHITESPACE))
			textComparator = RawTextComparator.WS_IGNORE_ALL;
		else
			textComparator = RawTextComparator.DEFAULT;

