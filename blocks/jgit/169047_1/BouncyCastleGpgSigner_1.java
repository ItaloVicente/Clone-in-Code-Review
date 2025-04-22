
	private String extractSignerId(String pgpUserId) {
		int from = pgpUserId.indexOf('<');
		if (from >= 0) {
			int to = pgpUserId.indexOf('>'
			if (to > from + 1) {
				return pgpUserId.substring(from + 1
			}
		}
		return pgpUserId;
	}
