		private void parseWildcards() {
			if (this.pattern.startsWith("*")) { //$NON-NLS-1$
				this.hasLeadingStar = true;
			}
			if (this.pattern.endsWith("*")) {//$NON-NLS-1$
				if (this.pattern.length() > 1 && this.pattern.charAt(this.pattern.length() - 2) != '\\') {
					this.hasTrailingStar = true;
				}
			}

			Vector<String> temp = new Vector<>();

			int pos = 0;
			StringBuilder buf = new StringBuilder();
			while (pos < this.pattern.length()) {
				char c = this.pattern.charAt(pos++);
				switch (c) {
				case '\\':
					if (pos >= this.pattern.length()) {
						buf.append(c);
					} else {
						char next = this.pattern.charAt(pos++);
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
						this.bound += buf.length();
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
				this.bound += buf.length();
			}

			this.fragments = new String[temp.size()];
			temp.copyInto(this.fragments);
		}

		boolean match(String text, int start, int end) {
			boolean found = false;
			if (fIgnoreWildCards) {
				if ((end - start == this.pattern.length())
						&& this.pattern.regionMatches(fIgnoreCase, 0, text, start, this.pattern.length()))
					return true;
				return false;
			}
			String[] segments = null;
			segments = this.fragments;
			int segCount = segments.length;
			if (segCount == 0 && (hasLeadingStar || hasTrailingStar)) {
				return true;
			}
			if (start == end) {
				if (this.pattern.length() == 0)
					return true;
				return false;
			}
			if (this.pattern.length() == 0) {
				if (start == end)
					return true;
				return false;
			}

			int tCurPos = start;
			int bound = end - this.bound;
			if (bound < 0) {
				return false;
			}
			int i = 0;
			String current = segments[i];
			int segLength = current.length();

			if (!hasLeadingStar) {
				if (!regExpRegionMatches(text, start, current, 0, segLength)) {
					return false;
				}
				++i;
				tCurPos = tCurPos + segLength;
			}
			if ((segments.length == 1) && (!hasLeadingStar) && (!hasTrailingStar)) {
				if (tCurPos == end)
					return true;
				return false;
			}
			while (i < segCount && found) {
				current = segments[i];
				int currentMatch;
				int k = current.indexOf(fSingleWildCard);
				if (k < 0) {
					currentMatch = textPosIn(text, tCurPos, end, current);
					if (currentMatch < 0) {
						found = false;
					}
				} else {
					currentMatch = regExpPosIn(text, tCurPos, end, current);
					if (currentMatch < 0) {
						found = false;
					}
				}
				if (!found)
					return false;
				tCurPos = currentMatch + current.length();
				i++;
			}

			if (!hasTrailingStar && tCurPos != end) {
				int clen = current.length();
				if (regExpRegionMatches(text, end - clen, current, 0, clen))
					return true;
				return false;
			}
			if (i == segCount)
				return true;
			return false;
		}

		public boolean matchTextWord(String text, int start, int end) {
			String[] textWords = getWords(text.substring(start, end));
			if (textWords.length == 0) {
				return pattern.isEmpty();
			}
			for (String subword : textWords) {
				if (match(subword, 0, subword.length())) {
					return true;
				}
			}
			return false;
		}
