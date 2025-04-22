		int pos = 0;
		StringBuilder buf = new StringBuilder();
		while (pos < fLength) {
			char c = fPattern.charAt(pos++);
			switch (c) {
			case '\\':
				if (pos >= fLength) {
					buf.append(c);
				} else {
					char next = fPattern.charAt(pos++);
					if (next == '*' || next == '?' || next == '\\') {
						buf.append(next);
					} else {
						buf.append(c);
						buf.append(next);
					}
				}
				break;
			case '*':
				if (buf.length() > 0) {
