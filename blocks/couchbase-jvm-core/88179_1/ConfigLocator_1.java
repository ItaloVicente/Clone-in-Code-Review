    private final AtomicLong counter;

    public ConfigLocator() {
        this(new Random().nextInt(1024));
    }

    ConfigLocator(final long initialValue) {
        counter = new AtomicLong(initialValue);
    }
