
    public static BuildIndexPath buildIndex() {
        return new DefaultBuildIndexPath();
    }

    public static UsingPath dropIndex(String namespace, String keyspace, String indexName) {
        return new DefaultDropPath().drop(namespace, keyspace, indexName);
    }

    public static UsingPath dropIndex(String keyspace, String indexName) {
        return new DefaultDropPath().drop(keyspace, indexName);
    }

    public static UsingPath dropPrimaryIndex(String namespace, String keyspace) {
        return new DefaultDropPath().dropPrimary(namespace, keyspace);
    }

    public static UsingPath dropPrimaryIndex(String keyspace) {
        return new DefaultDropPath().dropPrimary(keyspace);
    }
