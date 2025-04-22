		for (int j = 0; j < words.length; j++) {
			if (fIgnoreWildCards) {
				if ((end - start == words[j].pattern.length())
						&& words[j].pattern.regionMatches(fIgnoreCase, 0, text, start, words[j].pattern.length()))
					return true;
				continue;
			}
			segments = words[j].fragments;
			fHasLeadingStar = words[j].hasLeadingStar;
			fHasTrailingStar = words[j].hasTrailingStar;
			int segCount = segments.length;
			if (segCount == 0 && (fHasLeadingStar || fHasTrailingStar)) {
				return true;
			}
			if (start == end) {
				if (words[j].pattern.length() == 0)
					return true;
				continue;
			}
			if (words[j].pattern.length() == 0) {
				if (start == end)
					return true;
				continue;
			}

			int tlen = text.length();
			if (start < 0) {
				start = 0;
			}
			if (end > tlen) {
				end = tlen;
			}

			int tCurPos = start;
			int bound = end - words[j].bound;
			if (bound < 0) {
				continue;
			}
			int i = 0;
			String current = segments[i];
			int segLength = current.length();
			if (!fHasLeadingStar) {
				if (!regExpRegionMatches(text, start, current, 0, segLength)) {
					continue;
				}
				++i;
				tCurPos = tCurPos + segLength;
			}
			if ((segments.length == 1) && (!fHasLeadingStar) && (!fHasTrailingStar)) {
				if (tCurPos == end)
					return true;
				continue;

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
					continue;
				tCurPos = currentMatch + current.length();
				i++;
			}

			if (!fHasTrailingStar && tCurPos != end) {
				int clen = current.length();
				if (regExpRegionMatches(text, end - clen, current, 0, clen))
					return true;
				continue;
			}
			if (i == segCount)
				return true;
			continue;

		} // end of for loop
		return false;

	}

	private void parsePatternIntoWords() {
		String trimedPattern = fPattern.trim();
		wordsSplitted = trimedPattern.split("\\s+"); //$NON-NLS-1$
		words = new Word[wordsSplitted.length];
		for (int i = 0; i < wordsSplitted.length; i++) {
			words[i] = new Word();
			words[i].pattern = wordsSplitted[i];
