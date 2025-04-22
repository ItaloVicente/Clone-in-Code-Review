    private final AtomicLong counter;

    public QueryLocator() {
        this(new Random().nextInt(1024));
    }

    QueryLocator(final long initialValue) {
        counter = new AtomicLong(initialValue);
    }
