    private final AtomicLong counter;

    public ViewLocator() {
        this(new Random().nextInt(1024));
    }

    ViewLocator(final long initialValue) {
        counter = new AtomicLong(initialValue);
    }
