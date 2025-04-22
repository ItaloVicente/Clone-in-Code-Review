	private static final int S_GIT = 0;

	private static final int S_SSH = 1;

	private static final int S_SFTP = 2;

	private static final int S_HTTP = 3;

	private static final int S_HTTPS = 4;

	private static final int S_FTP = 5;

	private static final int S_FILE = 6;

	private static final String[] DEFAULT_SCHEMES;

	private static final String[] SCHEME_TOOLTIPS;

	static {
		DEFAULT_SCHEMES = new String[7];

		SCHEME_TOOLTIPS = new String[7];
		SCHEME_TOOLTIPS[S_GIT] = UIText.RepositorySelectionPage_tip_git;
		SCHEME_TOOLTIPS[S_SSH] = UIText.RepositorySelectionPage_tip_ssh;
		SCHEME_TOOLTIPS[S_SFTP] = UIText.RepositorySelectionPage_tip_sftp;
		SCHEME_TOOLTIPS[S_HTTP] = UIText.RepositorySelectionPage_tip_http;
		SCHEME_TOOLTIPS[S_HTTPS] = UIText.RepositorySelectionPage_tip_https;
		SCHEME_TOOLTIPS[S_FTP] = UIText.RepositorySelectionPage_tip_ftp;
		SCHEME_TOOLTIPS[S_FILE] = UIText.RepositorySelectionPage_tip_file;
	}

