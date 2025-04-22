package com.couchbase.client.java;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.cluster.AsyncClusterManager;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.transcoder.Transcoder;
import rx.Observable;

import java.util.List;

@InterfaceStability.Committed
@InterfaceAudience.Public
public interface AsyncCluster {

    Observable<AsyncBucket> openBucket();

    Observable<AsyncBucket> openBucket(String name);

    Observable<AsyncBucket> openBucket(String name, String password);

    Observable<AsyncBucket> openBucket(String name, String password, List<Transcoder<? extends Document, ?>> transcoders);

    Observable<AsyncClusterManager> clusterManager(String username, String password);

    Observable<Boolean> disconnect();

    Observable<ClusterFacade> core();

}
