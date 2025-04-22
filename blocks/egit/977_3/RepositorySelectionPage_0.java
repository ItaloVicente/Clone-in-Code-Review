	private enum Protocol {
		GIT("git", UIText.RepositorySelectionPage_tip_git, true, true, false), //$NON-NLS-1$
		SSH("ssh", UIText.RepositorySelectionPage_tip_ssh, true, true, true) { //$NON-NLS-1$
			@Override
			public boolean isMe(URIish uri) {
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
		},
		SFTP("sftp", UIText.RepositorySelectionPage_tip_sftp, true, true, true), //$NON-NLS-1$
		HTTP("http", UIText.RepositorySelectionPage_tip_http, true, true, true), //$NON-NLS-1$
		HTTPS("https", UIText.RepositorySelectionPage_tip_https, true, true, true), //$NON-NLS-1$
		FTP("ftp", UIText.RepositorySelectionPage_tip_ftp, true, true, true), //$NON-NLS-1$
		FILE("file", UIText.RepositorySelectionPage_tip_file, false, false, false) { //$NON-NLS-1$
			@Override
			public boolean isMe(URIish uri) {
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

		private final String defaultScheme;

		private final String tooltip;

		private final boolean hasHost;

		private final boolean hasPort;

		private final boolean canAuthenticate;

		private Protocol(String defaultScheme, String tooltip, boolean hasHost,
				boolean hasPort, boolean canAuthenticate) {
			this.defaultScheme = defaultScheme;
			this.tooltip = tooltip;
			this.hasHost = hasHost;
			this.hasPort = hasPort;
			this.canAuthenticate = canAuthenticate;
		}

		public boolean isMe(URIish uri) {
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

		public static Protocol fromScheme(String scheme) {
			Protocol[] protocols = Protocol.values();
			for (int i = 0; i < protocols.length; i++) {
				if (protocols[i].getDefaultScheme().equals(scheme))
					return protocols[i];
			}
			return null;
		}
	}

