	/**
	 * Construct a PersonIdent from a string with full name, email, time time
	 * zone string. The input string must be valid.
	 *
	 * @param in
	 *            a Git internal format author/committer string.
	 *
	 * @deprecated Use {@link RawParseUtils#parsePersonIdent(String)} instead.
	 */
	public PersonIdent(final String in) {
		final PersonIdent self = RawParseUtils.parsePersonIdent(in);
		if (self == null)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().malformedpersonIdentString, in));

		this.name = self.name;
		this.emailAddress = self.emailAddress;
		this.when = self.when;
		this.tzOffset = self.tzOffset;
	}

