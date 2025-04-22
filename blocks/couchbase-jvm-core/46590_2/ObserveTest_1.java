package com.couchbase.client.core.message.observe;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.ReplicaNotConfiguredException;
import com.couchbase.client.core.config.ClusterConfig;
import com.couchbase.client.core.config.CouchbaseBucketConfig;
import com.couchbase.client.core.message.CouchbaseResponse;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.cluster.GetClusterConfigRequest;
import com.couchbase.client.core.message.cluster.GetClusterConfigResponse;
import org.junit.Test;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ObserveTest {

    @Test(expected = ReplicaNotConfiguredException.class)
    public void shouldFailFastWhenReplicateToGreaterThanBucketReplicas() {
        ClusterFacade cluster = mock(ClusterFacade.class);

        CouchbaseBucketConfig bucketConfig = mock(CouchbaseBucketConfig.class);
        when(bucketConfig.numberOfReplicas()).thenReturn(0);
        ClusterConfig clusterConfig = mock(ClusterConfig.class);
        when(clusterConfig.bucketConfig("bucket")).thenReturn(bucketConfig);
        GetClusterConfigResponse clusterConfigResponse = new GetClusterConfigResponse(
            clusterConfig, ResponseStatus.SUCCESS
        );
        when(cluster.send(any(GetClusterConfigRequest.class))).thenReturn(
                Observable.just((CouchbaseResponse) clusterConfigResponse)
        );

        Observable<Boolean> result = Observe.call(
                cluster, "bucket", "id", 1234, false, Observe.PersistTo.NONE, Observe.ReplicateTo.ONE
        );
        result.toBlocking().single();
    }

    @Test(expected = ReplicaNotConfiguredException.class)
    public void shouldFailFastWhenPersistToGreaterThanBucketReplicas() {
        ClusterFacade cluster = mock(ClusterFacade.class);

        CouchbaseBucketConfig bucketConfig = mock(CouchbaseBucketConfig.class);
        when(bucketConfig.numberOfReplicas()).thenReturn(2);
        ClusterConfig clusterConfig = mock(ClusterConfig.class);
        when(clusterConfig.bucketConfig("bucket")).thenReturn(bucketConfig);
        GetClusterConfigResponse clusterConfigResponse = new GetClusterConfigResponse(
                clusterConfig, ResponseStatus.SUCCESS
        );
        when(cluster.send(any(GetClusterConfigRequest.class))).thenReturn(
                Observable.just((CouchbaseResponse) clusterConfigResponse)
        );

        Observable<Boolean> result = Observe.call(
                cluster, "bucket", "id", 1234, false, Observe.PersistTo.FOUR, Observe.ReplicateTo.NONE
        );
        result.toBlocking().single();
    }

}
