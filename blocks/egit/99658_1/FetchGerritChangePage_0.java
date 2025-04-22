	private static final Pattern GERRIT_FETCH_PATTERN = Pattern.compile(
			"git fetch (\\w+:\\S+) (refs/changes/\\d+/\\d+/\\d+) && git (\\w+) FETCH_HEAD"); //$NON-NLS-1$

	private static final Pattern GERRIT_URL_PATTERN = Pattern.compile(
			"(?:https?://\\S+?/|/)?([1-9][0-9]*)(?:/([1-9][0-9]*)(?:/([1-9][0-9]*)(?:\\.\\.\\d+)?)?)?(?:/\\S*)?"); //$NON-NLS-1$

	private static final Pattern GERRIT_CHANGE_REF_PATTERN = Pattern
			.compile("refs/changes/\\d+/(\\d+)(?:/\\d*)"); //$NON-NLS-1$

