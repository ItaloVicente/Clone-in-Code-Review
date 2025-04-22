	private String abbreviate(String id) {
		if (id.length() > 7) {
			return id.substring(0, 7);
		}
		return id;
	}

