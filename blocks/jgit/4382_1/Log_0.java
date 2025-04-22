	@Option(name = "--date"
	void dateFormat(String date) {
		if (date.toLowerCase().equals(date))
			date = date.toUpperCase();
		dateFormatter = new GitDateFormatter(Format.valueOf(date));
	}

