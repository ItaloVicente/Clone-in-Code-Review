    private final RedactionLevel redactionLevel;

    public CommonsLoggerFactory() {
        this(RedactionLevel.NONE);
    }

    public CommonsLoggerFactory(RedactionLevel redactionLevel) {
        this.redactionLevel = redactionLevel;
    }

