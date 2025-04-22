	private static final String DOT_GIT_EXT = ".git";
	private final KetchLeaderCache leaders;
	private Logger logger = LoggerFactory.getLogger(Fork.class);

	private File parentFolder;
	private final String source;
	private final String target;
	private final List<String> branches;
	private CredentialsProvider credentialsProvider;
	private final File hookDir;
	private final boolean sslVerify;

	public Fork(final File parentFolder
			final CredentialsProvider credentialsProvider

		this(parentFolder
				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
	}

	public Fork(final File parentFolder
			final CredentialsProvider credentialsProvider
			final boolean sslVerify) {
		this.parentFolder = checkNotNull("parentFolder"
		this.source = checkNotEmpty("source"
		this.target = checkNotEmpty("target"
		this.branches = branches;
		this.credentialsProvider = checkNotNull("credentialsProvider"
		this.leaders = leaders;

		this.hookDir = hookDir;

		this.sslVerify = sslVerify;
	}

	public Git execute() throws IOException {

		if (logger.isDebugEnabled()) {
			logger.debug("Forking repository <{}> to <{}>"
		}

		final File origin = new File(parentFolder
		final File destination = new File(parentFolder

		if (destination.exists()) {
			String message = String.format("Cannot fork because destination repository <%s> already exists"
			logger.error(message);
			throw new GitException(message);
		}

		return Git.clone(destination
				hookDir
	}
