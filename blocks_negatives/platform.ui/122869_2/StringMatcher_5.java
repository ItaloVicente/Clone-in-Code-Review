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
