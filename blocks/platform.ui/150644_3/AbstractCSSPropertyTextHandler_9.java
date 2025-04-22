		if ("uppercase".equals(textTransform) && textToInsert != null) {
			return textToInsert.toUpperCase();
		}
		if ("lowercase".equals(textTransform) && textToInsert != null) {
			return textToInsert.toLowerCase();
		}
		if ("inherit".equals(textTransform)) {
			return textToInsert;
		}

