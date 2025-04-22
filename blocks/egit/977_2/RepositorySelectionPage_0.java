	public enum Protocol {
		GIT("git", UIText.RepositorySelectionPage_tip_git, true, true, false), //$NON-NLS-1$
		SSH("ssh", UIText.RepositorySelectionPage_tip_ssh, true, true, true), //$NON-NLS-1$
		SFTP("sftp", UIText.RepositorySelectionPage_tip_sftp, true, true, true), //$NON-NLS-1$
		HTTP("http", UIText.RepositorySelectionPage_tip_http, true, true, true), //$NON-NLS-1$
		HTTPS("https", UIText.RepositorySelectionPage_tip_https, true, true, true), //$NON-NLS-1$
		FTP("ftp", UIText.RepositorySelectionPage_tip_ftp, true, true, true), //$NON-NLS-1$
		FILE("file", UIText.RepositorySelectionPage_tip_file, false, false, false); //$NON-NLS-1$

		private final String defaultScheme;
		private final String tooltip;
		private final boolean hasHost;
		private final boolean hasPort;
		private final boolean canAuthenticate;

		private Protocol(String defaultScheme, String tooltip, boolean hasHost, boolean hasPort, boolean canAuthenticate) {
			this.defaultScheme = defaultScheme;
			this.tooltip = tooltip;
			this.hasHost = hasHost;
			this.hasPort = hasPort;
			this.canAuthenticate = canAuthenticate;
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

		public static Protocol fromScheme(String scheme){
			Protocol[] protocols = Protocol.values();
			for (int i = 0; i < protocols.length; i++) {
				if (protocols[i].getDefaultScheme().equals(scheme))
					return protocols[i];
			}
			return null;
		}
	}

