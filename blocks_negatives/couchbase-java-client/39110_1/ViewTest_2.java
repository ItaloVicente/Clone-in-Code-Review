  private static final String seedNode = TestProperties.seedNode();
  private static final String bucketName = TestProperties.bucket();
  private static final String password = TestProperties.password();

  private static Bucket bucket;

  @BeforeClass
  public static void connect() {
    CouchbaseCluster cluster = new CouchbaseCluster(seedNode);
    bucket = cluster
      .openBucket(bucketName, password)
      .toBlockingObservable()
      .single();
  }
