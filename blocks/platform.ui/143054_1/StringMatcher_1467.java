		if (fPattern.endsWith("*")) {//$NON-NLS-1$
			if (fLength > 1 && fPattern.charAt(fLength - 2) != '\\') {
				fHasTrailingStar = true;
			}
		}

		ArrayList<String> temp = new ArrayList<>();

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
					temp.add(buf.toString());
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
		}

		if (buf.length() > 0) {
			temp.add(buf.toString());
			fBound += buf.length();
		}
		fSegments = temp.toArray(new String[temp.size()]);
	}

	protected int posIn(String text, int start, int end) {//no wild card in pattern
		int max = end - fLength;

		if (!fIgnoreCase) {
			int i = text.indexOf(fPattern, start);
			if (i == -1 || i > max) {
