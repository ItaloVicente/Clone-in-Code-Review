package com.couchbase.client.core.config.refresher;

import com.couchbase.client.core.cluster.Cluster;
import rx.Observable;

public class CarrierRefresher extends AbstractRefresher {

    public CarrierRefresher(final Cluster cluster) {
        super(cluster);
    }

    @Override
    public Observable<Boolean> registerBucket(final String name, final String password) {
        return null;
    }

    @Override
    public Observable<Boolean> deregisterBucket(final String name) {
        return null;
    }

    @Override
    public Observable<Boolean> shutdown() {
        return null;
    }
}
