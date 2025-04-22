			}
			tCurPos = currentMatch + current.length();
		}

		if (!fHasTrailingStar && tCurPos != end) {
			int clen = current.length();
			return regExpRegionMatches(text, end - clen, current, 0, clen);
		}
		return i == segCount;
	}

	private void parseNoWildCards() {
		fSegments = new String[1];
		fSegments[0] = fPattern;
		fBound = fLength;
	}

	private void parseWildCards() {
		if (fPattern.startsWith("*")) { //$NON-NLS-1$
