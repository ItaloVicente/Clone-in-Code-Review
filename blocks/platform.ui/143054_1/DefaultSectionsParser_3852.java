	protected String parseNumber(String heading) {
		int start = 0;
		int end = heading.length();
		char c;
		do {
			c = heading.charAt(start++);
		} while ((c == '.' || Character.isDigit(c)) && start < end);
