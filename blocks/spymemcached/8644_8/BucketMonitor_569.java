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
  public static final String CLIENT_SPEC_VER = "1.0";

  public BucketMonitor(URI cometStreamURI, String bucketname, String username,
      String password, ConfigurationParser configParser) {
    super();
    if (cometStreamURI == null) {
      throw new IllegalArgumentException("cometStreamURI cannot be NULL");
