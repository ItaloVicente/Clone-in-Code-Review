        int i = 0;
        String current = segments[i];
        int segLength = current.length();

        /* process first segment */
        if (!fHasLeadingStar) {
            if (!regExpRegionMatches(text, start, current, 0, segLength)) {
                continue;
            }
				++i;
				tCurPos = tCurPos + segLength;
        }
        if ((segments.length == 1) && (!fHasLeadingStar)
                && (!fHasTrailingStar)) {
        	if (tCurPos == end)
				return true;
			continue;
        }
        /* process middle segments */
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
