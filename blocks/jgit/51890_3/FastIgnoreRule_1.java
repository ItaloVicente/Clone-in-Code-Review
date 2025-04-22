			return;
		}
		if (pattern.charAt(0) == '\\' && pattern.length() > 1) {
			char next = pattern.charAt(1);
			if (next == '!' || next == '#') {
				pattern = pattern.substring(1);
