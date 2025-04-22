  private final List<URI> storedBaseList;
  private static final Logger LOGGER =
    Logger.getLogger(CouchbaseConnectionFactory.class.getName());
  private boolean needsReconnect;
  private volatile long thresholdLastCheck = System.nanoTime();
  private volatile int configThresholdCount = 0;
  private final int maxConfigCheck = 10; //maximum checks in 10 sec interval
