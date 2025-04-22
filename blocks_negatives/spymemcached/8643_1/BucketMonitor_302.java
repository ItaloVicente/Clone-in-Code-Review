    private final URI cometStreamURI;
    private Bucket bucket;
    private final String httpUser;
    private final String httpPass;
    private final ChannelFactory factory;
    private Channel channel;
    private final String host;
    private final int port;
    private ConfigurationParser configParser;
    private BucketUpdateResponseHandler handler;
    /**
     * The specification version which this client meets.  This will be included
     * in requests to the server.
     */
    public static final String CLIENT_SPEC_VER = "1.0";

    /**
     *
     * @param cometStreamURI the URI which will stream node changes
     * @param bucketname the bucketToMonitor name we are monitoring
     * @param username the username required for HTTP Basic Auth to the restful service
     * @param password the password required for HTTP Basic Auth to the restful service
     */
    public BucketMonitor(URI cometStreamURI,  String bucketname, String username, String password, ConfigurationParser configParser) {
        super();
        if (cometStreamURI == null) {
            throw new IllegalArgumentException("cometStreamURI cannot be NULL");
        }
        String scheme = cometStreamURI.getScheme() == null ? "http" : cometStreamURI.getScheme();
        if (!scheme.equals("http")) {
            throw new UnsupportedOperationException("Only http is supported.");
        }

        this.cometStreamURI = cometStreamURI;
        this.httpUser = username;
        this.httpPass = password;
        this.configParser = configParser;
        this.host = cometStreamURI.getHost();
        this.port = cometStreamURI.getPort() == -1 ? 80 : cometStreamURI.getPort();
        factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
