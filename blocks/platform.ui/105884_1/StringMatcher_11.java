		}
		return -1;
	}

	protected boolean regExpRegionMatches(String text, int tStart, String p, int pStart, int plen) {
		while (plen-- > 0) {
			char tchar = text.charAt(tStart++);
			char pchar = p.charAt(pStart++);

			if (!fIgnoreWildCards) {
				if (pchar == fSingleWildCard) {
					continue;
				}
			}
			if (pchar == tchar) {
