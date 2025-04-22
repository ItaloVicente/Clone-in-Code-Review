package com.couchbase.client.java.util.features;

public enum CouchbaseFeature {

    KV(1, 8, 0),
    VIEW(1, 8, 0),
    N1QL(1, 8, 0),
    CCCP(2, 5, 0),
    SSL(3, 0, 0),
    SPATIAL_QUERY(3, 0, 2);

    private final Version availableFrom;

    CouchbaseFeature(int major, int minor, int patch) {
        this.availableFrom = new Version(major, minor, patch);
    }

    public boolean isAvailableOn(Version serverVersion) {
        return serverVersion.compareTo(availableFrom) >= 0;
    }
}
