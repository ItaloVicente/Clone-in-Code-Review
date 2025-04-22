	private GitDateFormatter getDateFormatter() {
		if (dateFormatter == null)
			dateFormatter = new GitDateFormatter(format);
		return dateFormatter;
	}

