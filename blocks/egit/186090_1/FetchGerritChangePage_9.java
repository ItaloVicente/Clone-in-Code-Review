	@Override
	Pattern getProposalPattern(String input) {
		Change change = changeFromString(input);
		long changeNumber = -1;
		try {
			if (change == null) {
				Matcher matcher = DIGITS.matcher(input);
				if (matcher.find()) {
					return Pattern.compile(GERRIT_CHANGE_REF_PREFIX + "(../)?" //$NON-NLS-1$
							+ matcher.group() + WILDCARD);
				} else if (input.startsWith(GERRIT_CHANGE_REF_PREFIX)
						|| GERRIT_CHANGE_REF_PREFIX.startsWith(input)) {
					return null; // Match all
