package com.couchbase.client.core.config.loader;

import com.couchbase.client.core.config.BucketConfig;
import rx.Observable;

import java.net.InetAddress;
import java.util.Set;

public interface Loader {

    public Observable<BucketConfig> loadConfig(final Set<InetAddress> seedNodes, final String bucket,
                                               final String password);
}
