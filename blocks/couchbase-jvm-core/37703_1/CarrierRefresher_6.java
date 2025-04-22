package com.couchbase.client.core.config.refresher;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.cluster.Cluster;
import com.couchbase.client.core.config.BucketConfig;
import com.couchbase.client.core.config.parser.BucketConfigParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.subjects.PublishSubject;

public abstract  class AbstractRefresher implements Refresher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Refresher.class);

    private final PublishSubject<BucketConfig> configStream;

    private final Cluster cluster;

    protected AbstractRefresher(final Cluster cluster) {
        this.configStream = PublishSubject.create();
        this.cluster = cluster;
    }

    @Override
    public Observable<BucketConfig> configs() {
        return configStream;
    }

    protected void pushConfig(final String config) {
        try {
            configStream.onNext(BucketConfigParser.parse(config));
        } catch (CouchbaseException e) {
            LOGGER.warn("Exception while pushing new configuration - ignoring.", e);
        }
    }

    protected Cluster cluster() {
        return cluster;
    }

}
