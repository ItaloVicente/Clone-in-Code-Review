    /**
     * Creates a JGit filesystem provider which takes its configuration from the given ConfigProperties instance.
     * For a list of properties that affect the configuration of JGitFileSystemProvider, see the DEBUG log output of
     * this class during startup.
     */
    public JGitFileSystemProvider(final ConfigProperties gitPrefs,
                                  final ExecutorService executorService) {
        this.executorService = executorService;
