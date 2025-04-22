	private GitDateFormatter getDateFormatter() {
		if (dateFormatter == null) {
			dateFormatter = PreferenceBasedDateFormatter.create();
		}
		return dateFormatter;
	}

