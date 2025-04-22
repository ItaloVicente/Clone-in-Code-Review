    private static final ObjectMapper OBJECT_MAPPER = JacksonTransformers.MAPPER;

    private JsonObject value = null;
    private final byte[] byteValue;

    public DefaultAsyncN1qlQueryRow(byte[] value) {
        this.byteValue = value;
    }
