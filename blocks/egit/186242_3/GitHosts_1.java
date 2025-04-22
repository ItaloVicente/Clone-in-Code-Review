	public enum ServerType {

		GITHUB(GITHUB_ID, "refs/pull/", "/head", //$NON-NLS-1$ //$NON-NLS-2$
				GITHUB_PR_URL_PATTERN);

		public static final long NO_CHANGE_ID = -1;

		private final String id;

		private final String refPrefix;

		private final String refSuffix;

		private final Pattern urlPattern;

		private final Pattern refPattern;

		private final Pattern inputPattern;

		private ServerType(String id, String refPrefix, String refSuffix,
				Pattern urlPattern) {
			this.id = id;
			this.urlPattern = urlPattern;
			this.refPrefix = refPrefix;
			this.refSuffix = refSuffix;
			refPattern = Pattern.compile(refPrefix + "(\\d+)" + refSuffix); //$NON-NLS-1$
			inputPattern = refSuffix != null
					? Pattern
							.compile(refPrefix + "(\\d+)(?:" + refSuffix + ")?") //$NON-NLS-1$ //$NON-NLS-2$
					: refPattern;
		}

		public String getId() {
			return id;
		}

		public boolean uriMatches(String uri) {
			Collection<Pattern> patterns = URIS.get(this.getId());
			return patterns != null && patterns.stream()
					.anyMatch(p -> p.matcher(uri).matches());
		}

		public long fromRef(String ref) {
			try {
				if (ref == null) {
					return NO_CHANGE_ID;
				}
				Matcher m = refPattern.matcher(ref);
				if (!m.matches() || m.group(1) == null) {
					return NO_CHANGE_ID;
				}
				return Long.parseLong(m.group(1));
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				return NO_CHANGE_ID;
			}
		}

		public long fromString(String input) {
			if (input == null) {
				return NO_CHANGE_ID;
			}
			try {
				Matcher matcher = urlPattern.matcher(input);
				if (matcher.matches()) {
					return Long.parseLong(matcher.group(1));
				}
				matcher = inputPattern.matcher(input);
				if (matcher.matches()) {
					return Long.parseLong(matcher.group(1));
				}
				matcher = DIGITS.matcher(input);
				if (matcher.matches()) {
					return Long.parseLong(input);
				}
			} catch (NumberFormatException e) {
			}
			return NO_CHANGE_ID;
		}

		public String toFetchRef(long changeId) {
			if (changeId < 0) {
				return null;
			}
			return refSuffix == null ? refPrefix + changeId
					: refPrefix + changeId + refSuffix;
		}

		public String getRefPrefix() {
			return refPrefix;
		}
