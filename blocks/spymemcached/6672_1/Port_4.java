package net.spy.memcached.vbucket.config;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Pool {
    private URI base;
    private String name;
    private URI uri;
    private URI streamingUri;
    private Map<String, Bucket> buckets = new ConcurrentHashMap<String, Bucket>();
    private URI bucketsUri;

    public Pool() {
        super();
    }

    public Pool(String name, URI uri, URI streamingUri) {
        this.name = name;
        this.uri = uri;
        this.streamingUri = streamingUri;
    }

    public URI getBase() {
        return base;
    }

    public String getName() {
        return name;
    }

    public URI getUri() {
        return uri;
    }

    public URI getStreamingUri() {
        return streamingUri;
    }

    private Map<String, Bucket> getBuckets() {
        return buckets;
    }

    public URI getBucketsUri() {
        return bucketsUri;
    }

    void setBucketsUri(URI bucketsUri) {
        this.bucketsUri = bucketsUri;
    }
}
