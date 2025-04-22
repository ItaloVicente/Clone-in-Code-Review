package com.couchbase.client.java.util.features;

public enum CouchbaseFeature {

    KV(1, 8, 0),
    VIEW(2, 0, 0),
    N1QL(3, 5, 0),
    CCCP(2, 5, 0),
    SSL(3, 0, 0),
    SPATIAL_VIEW(3, 5, 0);

    private final Version availableFrom;

    CouchbaseFeature(int major, int minor, int patch) {
        this.availableFrom = new Version(major, minor, patch);
    }

    public boolean isAvailableOn(Version serverVersion) {
        return serverVersion.compareTo(availableFrom) >= 0;
    }
}
