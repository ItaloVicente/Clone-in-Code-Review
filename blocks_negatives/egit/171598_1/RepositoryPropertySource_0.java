		StringTokenizer tok = new StringTokenizer(keyString, "."); //$NON-NLS-1$

		String section;
		String subsection;
		String name;

		String[] valueList = null;
		if (tok.countTokens() == 2) {
			section = tok.nextToken();
			subsection = null;
			name = tok.nextToken();
		} else if (tok.countTokens() == 3) {
			section = tok.nextToken();
			subsection = tok.nextToken();
			name = tok.nextToken();
		} else {
