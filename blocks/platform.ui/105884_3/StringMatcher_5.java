	}

	private void parseNoWildCards() {
		fSegments = new String[1];
		fSegments[0] = fPattern;
		fBound = fLength;
		words = new Word[1];
		words[0].pattern = fPattern;
		words[0].bound = fLength;
		words[0].fragments = wordsSplitted;
	}

	private void parseWildCards() {
		if (fPattern.startsWith("*")) { //$NON-NLS-1$
			fHasLeadingStar = true;
