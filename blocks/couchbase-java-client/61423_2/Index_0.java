

    public static UsingPath dropNamedPrimaryIndex(String namespace, String keyspace, String customPrimaryName) {
        return new DefaultDropPath().drop(namespace, keyspace, customPrimaryName);
    }

    public static UsingPath dropNamedPrimaryIndex(String keyspace, String customPrimaryName) {
        return new DefaultDropPath().drop(keyspace, customPrimaryName);
    }
