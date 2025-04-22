	public static Pattern createProposalPattern(String content) {
		String patternString = content;
		while (patternString.length() > 0 && patternString.charAt(0) == ' ') {
			patternString = patternString.substring(1);
		}

		patternString = Pattern.quote(patternString);

		patternString = patternString.replaceAll("\\x2A", ".*"); //$NON-NLS-1$ //$NON-NLS-2$

		if (!patternString.endsWith(".*")) { //$NON-NLS-1$
			patternString = patternString + ".*"; //$NON-NLS-1$
		}

		Pattern pattern;
		try {
			pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
		} catch (PatternSyntaxException e) {
			pattern = null;
		}
		return pattern;
	}

