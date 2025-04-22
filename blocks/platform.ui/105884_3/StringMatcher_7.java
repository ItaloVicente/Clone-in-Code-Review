
		fSegments = new String[temp.size()];
		temp.copyInto(fSegments);
	}

	private void parseWildCardsForWords() {
		for (int i = 0; i < words.length; i++) {

			if (words[i].pattern.startsWith("*")) { //$NON-NLS-1$
				words[i].hasLeadingStar = true;
			}
			if (words[i].pattern.endsWith("*")) {//$NON-NLS-1$
				if (words[i].pattern.length() > 1 && words[i].pattern.charAt(words[i].pattern.length() - 2) != '\\') {
					words[i].hasTrailingStar = true;
