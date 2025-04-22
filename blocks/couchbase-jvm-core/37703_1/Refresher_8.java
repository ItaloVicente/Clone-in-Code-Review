package com.couchbase.client.core.config.refresher;

import com.couchbase.client.core.cluster.Cluster;
import com.couchbase.client.core.message.config.BucketStreamingRequest;
import com.couchbase.client.core.message.config.BucketStreamingResponse;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class HttpRefresher extends AbstractRefresher {

    private static final String TERSE_PATH = "/pools/default/bs/";
    private static final String VERBOSE_PATH = "/pools/default/bucketsStreaming/";

    public HttpRefresher(final Cluster cluster) {
        super(cluster);
    }

    @Override
    public Observable<Boolean> registerBucket(final String name, final String password) {
        return cluster()
            .<BucketStreamingResponse>send(new BucketStreamingRequest(TERSE_PATH, name, password))
            .onErrorResumeNext(new Func1<Throwable, Observable<BucketStreamingResponse>>() {
                   @Override
                   public Observable<BucketStreamingResponse> call(Throwable throwable) {
                       return cluster().send(new BucketStreamingRequest(VERBOSE_PATH, name, password));
                   }
               }
            )
            .map(new Func1<BucketStreamingResponse, Boolean>() {
                @Override
                public Boolean call(BucketStreamingResponse response) {
                    response.configs().subscribe(new Action1<String>() {
                        @Override
                        public void call(final String rawConfig) {
                            pushConfig(rawConfig);
                        }
                    });
                    return true;
                }
            });
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
