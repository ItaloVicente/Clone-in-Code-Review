  private static final String DEFAULT_MISSED_NOOPS_THRESHOLD = "3";
  private final short noopsThreshold;
  private volatile short outstandingNoops = 0;

  private static final Logger LOGGER = new LoggerProxy(
      LoggerFactory.getLogger(CouchbaseConfigConnection.class)
  );
