

    public static UsingPath dropCustomPrimaryIndex(String namespace, String keyspace, String customPrimaryName) {
        return new DefaultDropPath().drop(namespace, keyspace, customPrimaryName);
    }

    public static UsingPath dropCustomPrimaryIndex(String keyspace, String customPrimaryName) {
        return new DefaultDropPath().drop(keyspace, customPrimaryName);
    }
