    private static final String ALGORITHM = "SunX509";

    private final Environment env;

    public SSLEngineFactory(Environment env) {
        this.env = env;
    }

    public SSLEngine get() {
