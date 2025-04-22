  private final String name;
  private final URI uri;
  private final URI streamingUri;
  private URI bucketsUri;
  private final AtomicReference<Map<String, Bucket>> currentBuckets =
      new AtomicReference<Map<String, Bucket>>();
