		return match(text, 0, text.length());
	}

	public boolean match(String text, int start, int end) {
		boolean found = true;
		String[] segments = null;
		if (null == text) {
