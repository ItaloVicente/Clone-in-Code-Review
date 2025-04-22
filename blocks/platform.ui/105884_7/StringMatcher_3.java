	private void parseWildCardsForWords() {
		for (int i = 0; i < words.length; i++) {

			if (words[i].pattern.startsWith("*")) { //$NON-NLS-1$
				words[i].hasLeadingStar = true;
			}
			if (words[i].pattern.endsWith("*")) {//$NON-NLS-1$
				if (words[i].pattern.length() > 1 && words[i].pattern.charAt(words[i].pattern.length() - 2) != '\\') {
					words[i].hasTrailingStar = true;
				}
			}

			Vector temp = new Vector();

			int pos = 0;
			StringBuilder buf = new StringBuilder();
			while (pos < words[i].pattern.length()) {
				char c = words[i].pattern.charAt(pos++);
				switch (c) {
				case '\\':
					if (pos >= words[i].pattern.length()) {
						buf.append(c);
					} else {
						char next = words[i].pattern.charAt(pos++);
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
						words[i].bound += buf.length();
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
				temp.addElement(buf.toString());
				words[i].bound += buf.length();
			}

			words[i].fragments = new String[temp.size()];
			temp.copyInto(words[i].fragments);
		} // end of for loop
	}

