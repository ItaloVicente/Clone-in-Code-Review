		int i = 0;
		String current = fSegments[i];
		int segLength = current.length();

		if (!fHasLeadingStar) {
			if (!regExpRegionMatches(text, start, current, 0, segLength)) {
				return false;
			}
