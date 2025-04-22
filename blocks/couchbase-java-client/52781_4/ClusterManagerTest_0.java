    private static final String INSERT_BUCKET = "insertBucket";
    private static final String BUCKET_1 = "bucket1";
    private static final String BUCKET_2 = "bucket2";
    private static final String REMOVE_BUCKET = "removeBucket";
    private static final String UPDATE_BUCKET = "updateBucket";

    private static final Set<String> BUCKETS = new HashSet<String>(5);
    static {
        BUCKETS.add(INSERT_BUCKET);
        BUCKETS.add(BUCKET_1);
        BUCKETS.add(BUCKET_2);
        BUCKETS.add(REMOVE_BUCKET);
        BUCKETS.add(UPDATE_BUCKET);
    }

    private int bucketsRemaining = 0;

