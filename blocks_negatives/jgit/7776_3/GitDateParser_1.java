		try {
			dateFormat.setLenient(false);
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
