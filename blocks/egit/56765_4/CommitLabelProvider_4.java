		GitDateFormatter result = dateFormatter.get();
		if (result == null) {
			result = PreferenceBasedDateFormatter.create();
			dateFormatter.set(result);
		}
		return result;
