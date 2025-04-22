package com.couchbase.client.java;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.CouchbaseCore;
import com.couchbase.client.core.message.CouchbaseResponse;
import com.couchbase.client.core.message.cluster.DisconnectRequest;
import com.couchbase.client.core.message.cluster.DisconnectResponse;
import com.couchbase.client.core.message.cluster.OpenBucketRequest;
import com.couchbase.client.core.message.cluster.SeedNodesRequest;
import com.couchbase.client.java.cluster.AsyncClusterManager;
import com.couchbase.client.java.cluster.DefaultAsyncClusterManager;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.transcoder.Transcoder;
import rx.Observable;
import rx.functions.Func1;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CouchbaseAsyncCluster implements AsyncCluster {

    private static final String DEFAULT_BUCKET = "default";
    private static final String DEFAULT_HOST = "127.0.0.1";

    private final ClusterFacade core;
    private final CouchbaseEnvironment environment;
    private final ConnectionString connectionString;

    private final boolean sharedEnvironment;

    public static CouchbaseAsyncCluster create() {
        return create(DEFAULT_HOST);
    }

    public static CouchbaseAsyncCluster create(final CouchbaseEnvironment environment) {
        return create(environment, DEFAULT_HOST);
    }

    public static CouchbaseAsyncCluster create(final String... nodes) {
        return create(Arrays.asList(nodes));
    }

    public static CouchbaseAsyncCluster create(final List<String> nodes) {
        return new CouchbaseAsyncCluster(DefaultCouchbaseEnvironment.create(), ConnectionString.fromHostnames(nodes), false);
    }

    public static CouchbaseAsyncCluster create(final CouchbaseEnvironment environment, final String... nodes) {
        return create(environment, Arrays.asList(nodes));
    }

    public static CouchbaseAsyncCluster create(final CouchbaseEnvironment environment, final List<String> nodes) {
        return new CouchbaseAsyncCluster(environment, ConnectionString.fromHostnames(nodes), true);
    }

    public static CouchbaseAsyncCluster fromConnectionString(final String connectionString) {
        return new CouchbaseAsyncCluster(DefaultCouchbaseEnvironment.create(), ConnectionString.create(connectionString), false);
    }

    public static CouchbaseAsyncCluster fromConnectionString(final CouchbaseEnvironment environment, final String connectionString) {
        return new CouchbaseAsyncCluster(environment, ConnectionString.create(connectionString), true);
    }

    CouchbaseAsyncCluster(final CouchbaseEnvironment environment, final ConnectionString connectionString, final boolean sharedEnvironment) {
        this.sharedEnvironment = sharedEnvironment;
        core = new CouchbaseCore(environment);
        List<String> seedNodes = new ArrayList<String>();
        for (InetSocketAddress node : connectionString.hosts()) {
            seedNodes.add(node.getHostName());
        }
        if (seedNodes.isEmpty()) {
            seedNodes.add(DEFAULT_HOST);
        }
        SeedNodesRequest request = new SeedNodesRequest(seedNodes);
        core.send(request).toBlocking().single();
        this.environment = environment;
        this.connectionString = connectionString;
    }

    @Override
    public Observable<AsyncBucket> openBucket() {
        return openBucket(DEFAULT_BUCKET);
    }

    @Override
    public Observable<AsyncBucket> openBucket(final String name) {
        return openBucket(name, null);
    }

    @Override
    public Observable<AsyncBucket> openBucket(final String name, final String pass) {
        return openBucket(name, pass, null);
    }

    @Override
    public Observable<AsyncBucket> openBucket(final String name, String pass,
        final List<Transcoder<? extends Document, ?>> transcoders) {
        final String password = pass == null ? "" : pass;

        final List<Transcoder<? extends Document, ?>> trans = transcoders == null
            ? new ArrayList<Transcoder<? extends Document, ?>>() : transcoders;
        return core
            .send(new OpenBucketRequest(name, password))
            .map(new Func1<CouchbaseResponse, AsyncBucket>() {
                @Override
                public AsyncBucket call(CouchbaseResponse response) {
                    return new CouchbaseAsyncBucket(core, name, password, trans);
                }
            });
    }

    @Override
    public Observable<Boolean> disconnect() {
        return core
            .<DisconnectResponse>send(new DisconnectRequest())
            .flatMap(new Func1<DisconnectResponse, Observable<Boolean>>() {
                @Override
                public Observable<Boolean> call(DisconnectResponse disconnectResponse) {
                    return sharedEnvironment ? Observable.just(true) : environment.shutdown();
                }
            });
    }

    @Override
    public Observable<AsyncClusterManager> clusterManager(final String username, final String password) {
        return Observable.just((AsyncClusterManager) DefaultAsyncClusterManager.create(username, password, connectionString,
            environment, core));
    }

    @Override
    public Observable<ClusterFacade> core() {
        return Observable.just(core);
    }
}
