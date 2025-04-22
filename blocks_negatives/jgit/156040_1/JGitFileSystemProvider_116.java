    /**
     * Creates a JGit filesystem provider which takes its configuration from system properties. In a normal production
     * deployment, this is the constructor that will be invoked by the ServiceLoader mechanism.
     * For a list of properties that affect the configuration of JGitFileSystemProvider, see the DEBUG log output of
     * this class during startup.
     */
    public JGitFileSystemProvider() {
        this(new ConfigProperties(System.getProperties()),
             Executors.newCachedThreadPool(new DescriptiveThreadFactory()));
    }
