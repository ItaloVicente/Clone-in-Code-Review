package com.couchbase.client.core.config.loader;

import com.couchbase.client.core.cluster.Cluster;
import com.couchbase.client.core.env.Environment;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.config.BucketConfigResponse;
import com.couchbase.client.core.message.config.TerseBucketConfigRequest;
import com.couchbase.client.core.message.config.VerboseBucketConfigRequest;
import com.couchbase.client.core.service.ServiceType;
import rx.Observable;
import rx.functions.Func1;

import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class HttpLoader extends AbstractLoader{


    public HttpLoader(Cluster cluster, Environment environment, AtomicReference<List<InetAddress>> seedNodes) {
        super(cluster, environment, seedNodes);
    }

    @Override
    protected ServiceType serviceType() {
        return ServiceType.CONFIG;
    }

    @Override
    protected int port() {
        Environment env = environment();
        return env.sslEnabled() ? env.bootstrapHttpSslPort() : env.bootstrapHttpDirectPort();
    }

    @Override
    protected Observable<String> discoverConfig(final String bucket, final String password, final String hostname) {
        return cluster()
            .<BucketConfigResponse>send(new TerseBucketConfigRequest(hostname, bucket, password))
            .flatMap(new Func1<BucketConfigResponse, Observable<BucketConfigResponse>>() {
                @Override
                public Observable<BucketConfigResponse> call(BucketConfigResponse response) {
                    if (response.status() == ResponseStatus.SUCCESS) {
                        return Observable.just(response);
                    }
                    return cluster().send(new VerboseBucketConfigRequest(hostname, bucket, password));
                }
            }).map(new Func1<BucketConfigResponse, String>() {
                @Override
                public String call(BucketConfigResponse response) {
                    return response.config().replace("$HOST", hostname);
                }
            });
    }
}
