  private final String bucket;
  private final String password;
  private final ClusterFacade core;
  private final Map<Class<? extends Document>, Transcoder<? extends Document, ?>> transcoders;
  private final BucketManager bucketManager;


    public CouchbaseBucket(final ClusterFacade core, final String name, final String password,
