package com.couchbase.client.core.config.loader;

import com.couchbase.client.core.cluster.Cluster;
import com.couchbase.client.core.env.Environment;
import com.couchbase.client.core.message.binary.GetBucketConfigRequest;
import com.couchbase.client.core.message.binary.GetBucketConfigResponse;
import com.couchbase.client.core.service.ServiceType;
import io.netty.util.CharsetUtil;
import rx.Observable;
import rx.functions.Func1;

import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CarrierLoader extends AbstractLoader {

    public CarrierLoader(Cluster cluster, Environment environment, AtomicReference<List<InetAddress>> seedNodes) {
        super(cluster, environment, seedNodes);
    }

    @Override
    protected ServiceType serviceType() {
        return ServiceType.BINARY;
    }

    @Override
    protected int port() {
        Environment env = environment();
        return env.sslEnabled() ? env.bootstrapCarrierSslPort() : env.bootstrapCarrierDirectPort();
    }

    @Override
    protected Observable<String> discoverConfig(final String bucket, final String password, final String hostname) {
        return cluster()
            .<GetBucketConfigResponse>send(new GetBucketConfigRequest(bucket, hostname))
            .map(new Func1<GetBucketConfigResponse, String>() {
                @Override
                public String call(GetBucketConfigResponse response) {
                   return response.content().toString(CharsetUtil.UTF_8).replace("$HOST", hostname);
                }
            });
    }

}
