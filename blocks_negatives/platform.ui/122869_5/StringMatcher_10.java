    /**
	 * Parses the given words into segments separated by wildcard '*'
	 */
	private void parseWildCardsForWords() {
		for (int i = 0; i < words.length; i++) {

				words[i].hasLeadingStar = true;
			}
				/* make sure it's not an escaped wildcard */
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
						/* if it's an escape sequence */
						if (next == '*' || next == '?' || next == '\\') {
							buf.append(next);
						} else {
							/* not an escape sequence, just insert literally */
							buf.append(c);
							buf.append(next);
						}
					}
					break;
				case '*':
					if (buf.length() > 0) {
						/* new segment */
						temp.addElement(buf.toString());
						words[i].bound += buf.length();
						buf.setLength(0);
					}
					break;
				case '?':
					/* append special character representing single match wildcard */
					buf.append(fSingleWildCard);
					break;
				default:
					buf.append(c);
				}
			}

			/* add last buffer to segment list */
			if (buf.length() > 0) {
				temp.addElement(buf.toString());
				words[i].bound += buf.length();
			}

			words[i].fragments = new String[temp.size()];
			temp.copyInto(words[i].fragments);
	}
