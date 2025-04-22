	/**
	 * Pattern to match the sshd reply[1] against to determine whether it's a
	 * Gerrit.
	 * <p>
	 * [1]<a href=
	 * Gerrit 2.11 gerrit version ssh command</a>
	 * </p>
	 * <p>
	 * We match the whole reply from Gerrit's sshd (as opposed to a prefix match
	 * for "gerrit version") just in case a non-Gerrit has the great idea to
	 * return an error message like "gerrit version: unknown command" or some
	 * such on its stdout...
	 * </p>
	 */
	private static final Pattern GERRIT_SSHD_REPLY = Pattern
			.compile(GERRIT_SSHD_VERSION_API
					+ "\\s+(?:\\d+(?:\\.\\d+)+|.+-\\d+-g[0-9a-fA-F]{7,})"); //$NON-NLS-1$

