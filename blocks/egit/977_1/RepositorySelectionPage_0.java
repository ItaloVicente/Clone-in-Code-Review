	public static class Protocol {
		public static final int S_GIT = 0;
		public static final int S_SSH = 1;
		public static final int S_SFTP = 2;
		public static final int S_HTTP = 3;
		public static final int S_HTTPS = 4;
		public static final int S_FTP = 5;
		public static final int S_FILE = 6;

		private static final List<Protocol> protocols = new ArrayList<Protocol>();

		static {
			protocols.add(new Protocol("git", UIText.RepositorySelectionPage_tip_git, true, true, false)); //$NON-NLS-1$
			protocols.add(new Protocol("ssh", UIText.RepositorySelectionPage_tip_git, true, true, true)); //$NON-NLS-1$
			protocols.add(new Protocol("sftp", UIText.RepositorySelectionPage_tip_git, true, true, true)); //$NON-NLS-1$
			protocols.add(new Protocol("http", UIText.RepositorySelectionPage_tip_git, true, true, true)); //$NON-NLS-1$
			protocols.add(new Protocol("https", UIText.RepositorySelectionPage_tip_git, true, true, true)); //$NON-NLS-1$
			protocols.add(new Protocol("ftp", UIText.RepositorySelectionPage_tip_git, true, true, true)); //$NON-NLS-1$
			protocols.add(new Protocol("file", UIText.RepositorySelectionPage_tip_git, false, false, false)); //$NON-NLS-1$
		}

		public static List<Protocol> getSupportedProtocols() {
			return protocols;
		}

		private final String scheme;
		private final String tooltip;
		private final boolean hasHost;
		private final boolean hasPort;
		private final boolean canAuthenticate;

		private Protocol(String scheme, String tooltip, boolean hasHost, boolean hasPort, boolean canAuthenticate) {
			this.scheme = scheme;
			this.tooltip = tooltip;
			this.hasHost = hasHost;
			this.hasPort = hasPort;
			this.canAuthenticate = canAuthenticate;
		}

		public String getScheme() {
			return scheme;
		}

		public String getTooltip() {
			return tooltip;
		}

		public boolean hasHost() {
			return hasHost;
		}

		public boolean hasPort() {
			return hasPort;
		}

		public boolean canAuthenticate() {
			return canAuthenticate;
		}
	}

