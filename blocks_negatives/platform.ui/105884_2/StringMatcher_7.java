        int i = 0;
        String current = fSegments[i];
        int segLength = current.length();

        /* process first segment */
        if (!fHasLeadingStar) {
            if (!regExpRegionMatches(text, start, current, 0, segLength)) {
                return false;
            } else {
                ++i;
                tCurPos = tCurPos + segLength;
            }
        }
        if ((fSegments.length == 1) && (!fHasLeadingStar)
                && (!fHasTrailingStar)) {
            return tCurPos == end;
        }
        /* process middle segments */
        while (i < segCount) {
            current = fSegments[i];
            int currentMatch;
            int k = current.indexOf(fSingleWildCard);
            if (k < 0) {
                currentMatch = textPosIn(text, tCurPos, end, current);
                if (currentMatch < 0) {
					return false;
