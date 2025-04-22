	public static class Protocol {
		private static final ArrayList<Protocol> protocols = new ArrayList<Protocol>();

		public static final Protocol GIT = new Protocol(1, "git", //$NON-NLS-1$
				UIText.RepositorySelectionPage_tip_git, true, true, false);

		public static final Protocol SSH = new Protocol(2, "ssh", //$NON-NLS-1$
				UIText.RepositorySelectionPage_tip_ssh, true, true, true) {
			@Override
			public boolean handles(URIish uri) {
				if (!uri.isRemote())
					return false;
				final String scheme = uri.getScheme();
				if (getDefaultScheme().equals(scheme))
					return true;
				if ("ssh+git".equals(scheme)) //$NON-NLS-1$
					return true;
				if ("git+ssh".equals(scheme)) //$NON-NLS-1$
					return true;
				if (scheme == null && uri.getHost() != null
						&& uri.getPath() != null)
					return true;
				return false;
			}
		};

		public static final Protocol SFTP = new Protocol(3, "sftp", //$NON-NLS-1$
				UIText.RepositorySelectionPage_tip_sftp, true, true, true);

		public static final Protocol HTTP = new Protocol(4, "http", //$NON-NLS-1$
				UIText.RepositorySelectionPage_tip_http, true, true, true);

		public static final Protocol HTTPS = new Protocol(5, "https", //$NON-NLS-1$
				UIText.RepositorySelectionPage_tip_https, true, true, true);

		public static final Protocol FTP = new Protocol(6, "ftp", //$NON-NLS-1$
				UIText.RepositorySelectionPage_tip_ftp, true, true, true);

		public static final Protocol FILE = new Protocol(7, "file", //$NON-NLS-1$
				UIText.RepositorySelectionPage_tip_file, false, false, false) {
			@Override
			public boolean handles(URIish uri) {
				if (getDefaultScheme().equals(uri.getScheme())
						|| uri.getScheme() == null)
					return true;
				if (uri.getHost() != null || uri.getPort() > 0
						|| uri.getUser() != null || uri.getPass() != null
						|| uri.getPath() == null)
					return false;
				if (uri.getScheme() == null)
					return FS.DETECTED
							.resolve(new File("."), uri.getPath()).isDirectory(); //$NON-NLS-1$
				return false;
			}
		};

		private final int ordinal;

		private final String defaultScheme;

		private final String tooltip;

		private final boolean hasHost;

		private final boolean hasPort;

		private final boolean canAuthenticate;

		private Protocol(int ordinal, String defaultScheme, String tooltip,
				boolean hasHost, boolean hasPort, boolean canAuthenticate) {
			this.ordinal = ordinal;
			this.defaultScheme = defaultScheme;
			this.tooltip = tooltip;
			this.hasHost = hasHost;
			this.hasPort = hasPort;
			this.canAuthenticate = canAuthenticate;
			protocols.add(ordinal, this);
		}

		public boolean handles(URIish uri) {
			return getDefaultScheme().equals(uri.getScheme());
		}

		public String getDefaultScheme() {
			return defaultScheme;
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

		public int ordinal() {
			return ordinal;
		}

		public static Protocol[] values() {
			return (Protocol[]) protocols.toArray();
		}

		public static Protocol fromDefaultScheme(String scheme) {
			for (Protocol p : Protocol.values()) {
				if (p.getDefaultScheme().equals(scheme))
					return p;
			}
			return null;
		}
	}

