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

    public Fork(final File parentFolder,
                final String source,
                final String target,
                final List<String> branches,
                final CredentialsProvider credentialsProvider,
                final KetchLeaderCache leaders,
                final File hookDir) {

        this(parentFolder,
             source,
             target,
             branches,
             credentialsProvider,
             leaders,
             hookDir,
             JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
    }

    public Fork(final File parentFolder,
                final String source,
                final String target,
                final List<String> branches,
                final CredentialsProvider credentialsProvider,
                final KetchLeaderCache leaders,
                final File hookDir,
                final boolean sslVerify) {
        this.parentFolder = checkNotNull("parentFolder",
                                         parentFolder);
        this.source = checkNotEmpty("source",
                                    source);
        this.target = checkNotEmpty("target",
                                    target);
        this.branches = branches;
        this.credentialsProvider = checkNotNull("credentialsProvider",
                                                credentialsProvider);
        this.leaders = leaders;

        this.hookDir = hookDir;

        this.sslVerify = sslVerify;
    }

    public Git execute() throws IOException {

        if (logger.isDebugEnabled()) {
            logger.debug("Forking repository <{}> to <{}>",
                         source,
                         target);
        }

        final File origin = new File(parentFolder,
                                     source + DOT_GIT_EXT);
        final File destination = new File(parentFolder,
                                          target + DOT_GIT_EXT);

        if (destination.exists()) {
            String message = String.format("Cannot fork because destination repository <%s> already exists",
                                           target);
            logger.error(message);
            throw new GitException(message);
        }

        return Git.clone(destination,
                         origin.toPath().toUri().toString(),
                         false,
                         branches,
                         credentialsProvider,
                         leaders,
                         hookDir,
                         sslVerify);
    }
