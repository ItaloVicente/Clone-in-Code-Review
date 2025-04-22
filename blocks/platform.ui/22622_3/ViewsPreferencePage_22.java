
		String result = themeAssociations.get(themeId);

		if (result == null) {
			for (Map.Entry<String, String> entry : themeAssociations.entrySet()) {
				if (themeId.startsWith(entry.getKey())) {
					return entry.getValue();
				}
			}
