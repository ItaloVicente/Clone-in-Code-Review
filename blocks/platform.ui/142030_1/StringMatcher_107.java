		}
		if ((fSegments.length == 1) && (!fHasLeadingStar) && (!fHasTrailingStar)) {
			return tCurPos == end;
		}
		for (; i < segCount && tCurPos <= bound; ++i) {
			current = fSegments[i];
			int currentMatch;
			int k = current.indexOf(fSingleWildCard);
			if (k < 0) {
				currentMatch = textPosIn(text, tCurPos, end, current);
				if (currentMatch < 0) {
