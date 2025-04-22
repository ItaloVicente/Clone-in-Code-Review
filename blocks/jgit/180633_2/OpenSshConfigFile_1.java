		int length = value.length();
		for (int i = 0; i < length; i++) {
			char ch = value.charAt(i);
			if (!Character.isWhitespace(ch)) {
				b.append(ch);
			}
