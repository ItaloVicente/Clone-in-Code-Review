    protected static final byte QUERY_STATE_INITIAL = 0;
    protected static final byte QUERY_STATE_SIGNATURE = 1;
    protected static final byte QUERY_STATE_ROWS = 2;
    protected static final byte QUERY_STATE_ROWS_RAW = 20;
    protected static final byte QUERY_STATE_ROWS_DECIDE = 29;
    protected static final byte QUERY_STATE_ERROR = 3;
    protected static final byte QUERY_STATE_WARNING = 4;
    protected static final byte QUERY_STATE_STATUS = 5;
    protected static final byte QUERY_STATE_INFO = 6;
    protected static final byte QUERY_STATE_DONE = 8;

    /**
     * This is the number of characters expected to be present to be able to read
     * the beginning of the JSON, including the "requestID" token and its value
     * (currently expected to be 36 chars, but the code is adaptative).
     */
    private static final int MINIMUM_WINDOW_FOR_REQUESTID = 55;

    /**
     * This is a window of characters allowing to detect the clientContextID token
     * (including room for JSON separators, etc...).
     */
    public static final int MINIMUM_WINDOW_FOR_CLIENTID_TOKEN = 27;

