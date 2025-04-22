	private boolean listenToDateFormatChanges;

	private IPropertyChangeListener dateFormatListener = event -> {
		if (!listenToDateFormatChanges) {
			return;
		}
		String property = event.getProperty();
		if (property == null) {
			return;
		}
		switch (property) {
		case UIPreferences.DATE_FORMAT:
		case UIPreferences.DATE_FORMAT_CHOICE:
			refreshPage();
			break;
		default:
			break;
		}
	};

