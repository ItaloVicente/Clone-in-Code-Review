	private final File repoDir;
	private final File hookDir;
	private final KetchLeaderCache leaders;
	private final boolean sslVerify;

	public CreateRepository(final File repoDir) {
		this(repoDir
	}

	public CreateRepository(final File repoDir
		this(repoDir
	}

	public CreateRepository(final File repoDir
		this(repoDir
	}

	public CreateRepository(final File repoDir
		this(repoDir
	}

	public CreateRepository(final File repoDir
		this(repoDir
	}

	public CreateRepository(final File repoDir
			final boolean sslVerify) {
		this.repoDir = repoDir;
		this.hookDir = hookDir;
		this.leaders = leaders;
		this.sslVerify = sslVerify;
	}

	public Optional<Git> execute() throws IOException {
		try {
			boolean newRepository = !repoDir.exists();
			final org.eclipse.jgit.api.Git _git = org.eclipse.jgit.api.Git.init().setBare(true).setDirectory(repoDir)
					.call();

			if (leaders != null) {
				new WriteConfiguration(_git.getRepository()
					cfg.setInt("core"
					cfg.setString("extensions"
				}).execute();
			}

			final Repository repo = new FileRepositoryBuilder().setGitDir(repoDir).build();

			final org.eclipse.jgit.api.Git git = new org.eclipse.jgit.api.Git(repo);

			setupSSLVerify(repo);

			if (setupGitHooks(newRepository)) {
				final File repoHookDir = new File(repoDir

				try {
					FileUtils.copyDirectory(hookDir
				} catch (final Exception ex) {
					throw new RuntimeException(ex);
				}

				for (final File file : repoHookDir.listFiles()) {
					if (file != null && file.isFile()) {
						file.setExecutable(true);
					}
				}
			}

			return Optional.of(new GitImpl(git
		} catch (final Exception ex) {
			throw new IOException(ex);
		}
	}

	private void setupSSLVerify(Repository repo) throws java.io.IOException {
		StoredConfig config = repo.getConfig();
		config.setBoolean("http"
		config.save();
	}

	private boolean setupGitHooks(boolean newRepository) {
		return newRepository && hookDir != null;
	}
