		Vector temp = new Vector();

		int pos = 0;
		StringBuffer buf = new StringBuffer();
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
					temp.addElement(buf.toString());
					fBound += buf.length();
					buf.setLength(0);
				}
				break;
			case '?':
				buf.append(fSingleWildCard);
				break;
			default:
				buf.append(c);
			}
