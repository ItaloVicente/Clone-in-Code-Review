	private static final Pattern GERRIT_CHANGE_PROPOSAL_PATTERN = Pattern
			.compile("refs/changes/(\\d\\d)(/([1-9][0-9]*))?.*"); //$NON-NLS-1$

	private static final Pattern DIGITS = Pattern
			.compile("\\h*0*([1-9]\\d*)\\h*"); //$NON-NLS-1$

