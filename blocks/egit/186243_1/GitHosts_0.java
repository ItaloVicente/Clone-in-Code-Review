	private static final String GITLAB_ID = "gitlab"; //$NON-NLS-1$

	private static final Pattern GITLAB_REMOTE_URL_PATTERN = Pattern.compile(
			"(?:(?:https?|ssh)://)?(?:[^@:/]+(?::[^@:/]*)?@)?gitlab.com[:/][^/]+/.*\\.git"); //$NON-NLS-1$

	private static final Pattern ECLIPSE_GITLAB_REMOTE_URL_PATTERN = Pattern
			.compile(
					"(?:(?:https?|ssh)://)?(?:[^@:/]+(?::[^@:/]*)?@)?gitlab.eclipse.org[:/][^/]+/.*\\.git"); //$NON-NLS-1$

	private static final Pattern GITLAB_PR_URL_PATTERN = Pattern
			.compile("https?://.*/merge_requests/(\\d+)"); //$NON-NLS-1$

