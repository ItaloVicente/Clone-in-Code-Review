    /**
     * True if N1QL querying should be enabled manually, deprecated.
     *
     * With Couchbase Server 4.0 and onward, it will be automatically detected.
     *
     * @return true if manual N1QL querying is enabled.
     * @deprecated
     */
    @Deprecated
    boolean queryEnabled();

    /**
     * If manual querying enabled, this defines the N1QL port to use, deprecated.
     *
     * With Couchbase Server 4.0 and onward, it will be automatically detected.
     *
     * @return the query port.
     * @deprecated
     */
    @Deprecated
    int queryPort();

