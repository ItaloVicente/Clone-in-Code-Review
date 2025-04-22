	private static final Pattern GITHUB_PR_URL_PATTERN = Pattern
			.compile("https?://.*/pull/(\\d+)"); //$NON-NLS-1$

	private static final Pattern DIGITS = Pattern.compile("\\d+"); //$NON-NLS-1$

	private static final Map<String, Collection<Pattern>> URIS = new ConcurrentHashMap<>();

	static {
		addServerPattern(GITHUB_ID, GITHUB_REMOTE_URL_PATTERN);
	}

