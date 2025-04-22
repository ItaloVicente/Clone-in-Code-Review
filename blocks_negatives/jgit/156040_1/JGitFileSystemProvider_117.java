    /**
     * Creates a JGit filesystem provider which takes its configuration from the given map.
     * For a list of properties that affect the configuration of JGitFileSystemProvider, see the DEBUG log output of
     * this class during startup.
     */
    public JGitFileSystemProvider(final Map<String, String> gitPrefs) throws IOException {
        this(new ConfigProperties(gitPrefs),
             Executors.newCachedThreadPool(new DescriptiveThreadFactory()));
    }
