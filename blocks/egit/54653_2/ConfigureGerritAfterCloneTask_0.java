	private static final int GERRIT_SSHD_DEFAULT_PORT = 29418;

	private static final String GERRIT_SSHD_VERSION_API = "gerrit version"; //$NON-NLS-1$

	private static final Pattern GERRIT_SSHD_REPLY = Pattern
			.compile(GERRIT_SSHD_VERSION_API
					+ "\\s+(?:\\d+(?:\\.\\d+)+|.+-\\d+-g[0-9a-fA-F]{7})"); //$NON-NLS-1$

