package com.couchbase.client.core.config.refresher;

import com.couchbase.client.core.config.BucketConfig;
import com.couchbase.client.core.config.ConfigurationProvider;
import rx.Observable;

public interface Refresher {

    Observable<BucketConfig> configs();

    Observable<Boolean> registerBucket(String name, String password);

    Observable<Boolean> deregisterBucket(String name);

    Observable<Boolean> shutdown();

}
