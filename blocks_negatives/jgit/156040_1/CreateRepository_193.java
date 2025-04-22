    private final File repoDir;
    private final File hookDir;
    private final KetchLeaderCache leaders;
    private final boolean sslVerify;

    public CreateRepository(final File repoDir) {
        this(repoDir,
             null,
             null,
             JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
    }

    public CreateRepository(final File repoDir,
                            final boolean sslVerify) {
        this(repoDir,
             null,
             null,
             sslVerify);
    }

    public CreateRepository(final File repoDir,
                            final File hookDir) {
        this(repoDir,
             hookDir,
             null,
             JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
    }

    public CreateRepository(final File repoDir,
                            final File hookDir,
                            final boolean sslVerify) {
        this(repoDir,
             hookDir,
             null,
             sslVerify);
    }

    public CreateRepository(final File repoDir,
                            final File hookDir,
                            final KetchLeaderCache leaders) {
        this(repoDir,
             hookDir,
             leaders,
             JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
    }

    public CreateRepository(final File repoDir,
                            final File hookDir,
                            final KetchLeaderCache leaders,
                            final boolean sslVerify) {
        this.repoDir = repoDir;
        this.hookDir = hookDir;
        this.leaders = leaders;
        this.sslVerify = sslVerify;
    }

    public Optional<Git> execute() throws IOException {
        try {
            boolean newRepository = !repoDir.exists();
            final org.eclipse.jgit.api.Git _git = org.eclipse.jgit.api.Git.init().setBare(true).setDirectory(repoDir).call();

            if (leaders != null) {
                new WriteConfiguration(_git.getRepository(),
                                       cfg -> {
                                           cfg.setInt("core",
                                                      null,
                                                      "repositoryformatversion",
                                                      1);
                                           cfg.setString("extensions",
                                                         null,
                                                         "refsStorage",
                                                         "reftree");
                                       }).execute();
            }

            final Repository repo = new FileRepositoryBuilder()
                    .setGitDir(repoDir)
                    .build();

            final org.eclipse.jgit.api.Git git = new org.eclipse.jgit.api.Git(repo);

            setupSSLVerify(repo);

            if (setupGitHooks(newRepository)) {
                final File repoHookDir = new File(repoDir,
                                                  "hooks");

                try {
                    FileUtils.copyDirectory(hookDir,
                                            repoHookDir);
                } catch (final Exception ex) {
                    throw new RuntimeException(ex);
                }

                for (final File file : repoHookDir.listFiles()) {
                    if (file != null && file.isFile()) {
                        file.setExecutable(true);
                    }
                }
            }

            return Optional.of(new GitImpl(git,
                                           leaders));
        } catch (final Exception ex) {
            throw new IOException(ex);
        }
    }

    private void setupSSLVerify(Repository repo) throws java.io.IOException {
        StoredConfig config = repo.getConfig();
        config.setBoolean("http", null, "sslVerify", sslVerify);
        config.save();
    }

    private boolean setupGitHooks(boolean newRepository) {
        return newRepository && hookDir != null;
    }
