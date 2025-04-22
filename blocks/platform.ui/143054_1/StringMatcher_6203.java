		}

		return -1;
	}

	protected int regExpPosIn(String text, int start, int end, String p) {
		int plen = p.length();

		int max = end - plen;
		for (int i = start; i <= max; ++i) {
			if (regExpRegionMatches(text, i, p, 0, plen)) {
