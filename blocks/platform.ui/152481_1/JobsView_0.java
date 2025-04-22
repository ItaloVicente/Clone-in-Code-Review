		return parseDuration(durationField.getText(), 3000);
	}

	protected long parseDuration(String durationStr) {
		if (durationStr == null || durationStr.isEmpty()) {
			return -1;
