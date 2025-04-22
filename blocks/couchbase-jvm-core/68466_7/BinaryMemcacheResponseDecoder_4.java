    private static short MAX_KEY_LENGTH;

    private static int MAX_VALUE_LENGTH;

    static {
        MAX_KEY_LENGTH = Short.parseShort(System.getProperty("com.couchbase.maxKVKeySize", "250"));

        MAX_VALUE_LENGTH = Integer.parseInt(System.getProperty("com.couchbase.maxKVValueSize", "250")) * (int)Math.pow(10, 6);
    }

