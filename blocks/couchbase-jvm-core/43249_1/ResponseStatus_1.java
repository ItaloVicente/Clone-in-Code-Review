    FAILURE_BUSY(204, "The server is busy"),

    FAILURE_NOT_SUPPORTED(205, "The server does not support this request"),

    FAILURE_KV_NOT_STORED(301, "Entity not stored");

    private final int code;
    private final String description;

    ResponseStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
