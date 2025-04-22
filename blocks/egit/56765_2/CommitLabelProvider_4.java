		GitDateFormatter result = dateFormatter.get();
		if (result == null) {
			result = new PreferenceBasedDateFormatter();
			dateFormatter.set(result);
		}
		return result;
