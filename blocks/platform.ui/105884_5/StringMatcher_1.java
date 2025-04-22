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
