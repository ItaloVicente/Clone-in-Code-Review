	private final File repoDir;
	private final String origin;
	private final List<String> branches;
	private final CredentialsProvider credentialsProvider;
	private final boolean isMirror;
	private final KetchLeaderCache leaders;
	private final File hookDir;
	private final boolean sslVerify;

	private Logger logger = LoggerFactory.getLogger(Clone.class);

	public Clone(final File directory
			final CredentialsProvider credentialsProvider
		this(directory
				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
	}

	public Clone(final File directory
			final CredentialsProvider credentialsProvider
			final boolean sslVerify) {
		this.repoDir = checkNotNull("directory"
		this.origin = checkNotEmpty("origin"
		this.isMirror = isMirror;
		this.branches = branches;
		this.credentialsProvider = credentialsProvider;
		this.leaders = leaders;
		this.hookDir = hookDir;
		this.sslVerify = sslVerify;
	}

	public Optional<Git> execute() throws IOException {

		if (repoDir.exists()) {
			String message = String.format("Cannot clone because destination repository <%s> already exists"
					repoDir.getAbsolutePath());
			logger.error(message);
			throw new CloneException(message);
		}

		final Git git = Git.createRepository(repoDir

		if (git != null) {
			try {

				final Collection<RefSpec> refSpecList;
				if (isMirror) {
					refSpecList = singletonList(new RefSpec(REFS_MIRRORED));
				} else {
					refSpecList = emptyList();
				}
				final Map.Entry<String
				git.fetch(credentialsProvider

				git.syncRemote(remote);

				if (git.isKetchEnabled()) {
					git.convertRefTree();
					git.updateLeaders(leaders);
				}

				git.setHeadAsInitialized();

				BranchUtil.deleteUnfilteredBranches(git.getRepository()

				return Optional.of(git);
			} catch (Exception e) {
				String message = String.format("Error cloning origin <%s>."
				logger.error(message);
				cleanupDir(git.getRepository().getDirectory());
				throw new CloneException(message
			}
		}

		return Optional.empty();
	}

	private void cleanupDir(final File gitDir) throws IOException {

		try {
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				new WindowCacheConfig().install();
			}
			FileUtils.delete(gitDir
		} catch (java.io.IOException e) {
			throw new IOException("Failed to remove the git repository."
		}
	}

	public static class CloneException extends RuntimeException {

		public CloneException(final String message) {
			super(message);
		}

		public CloneException(final String message
			super(message
		}
	}
