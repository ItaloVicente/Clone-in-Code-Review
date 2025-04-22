package com.couchbase.client.core.cluster;

import com.couchbase.client.core.ReplicaNotConfiguredException;
import com.couchbase.client.core.config.CouchbaseBucketConfig;
import com.couchbase.client.core.message.cluster.GetClusterConfigRequest;
import com.couchbase.client.core.message.cluster.GetClusterConfigResponse;
import com.couchbase.client.core.message.kv.InsertRequest;
import com.couchbase.client.core.message.kv.InsertResponse;
import com.couchbase.client.core.message.kv.RemoveRequest;
import com.couchbase.client.core.message.kv.RemoveResponse;
import com.couchbase.client.core.message.observe.Observe;
import com.couchbase.client.core.retry.BestEffortRetryStrategy;
import com.couchbase.client.core.util.ClusterDependentTest;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class ObserveTest extends ClusterDependentTest {

    private CouchbaseBucketConfig config = null;
    private int numberOfReplicas;
    private int numberOfNodes;

    @Before
    public void gatherClusterInfo() {
        if (config == null) {
            GetClusterConfigResponse res = cluster().<GetClusterConfigResponse>send(new GetClusterConfigRequest())
                .toBlocking().single();
            config = (CouchbaseBucketConfig) res.config().bucketConfig(bucket());
            numberOfNodes = config.nodes().size();
            numberOfReplicas = config.numberOfReplicas();
        }
    }


    @Test
    public void shouldObservePersistToMaster() {
        InsertRequest request = new InsertRequest("persInsDoc1", Unpooled.copiedBuffer("test", CharsetUtil.UTF_8), bucket());
        InsertResponse response = cluster().<InsertResponse>send(request).toBlocking().single();
        assertTrue(response.status().isSuccess());
        ReferenceCountUtil.release(response);

        Boolean observeSuccess = Observe.call(
                cluster(),
                bucket(),
                "persInsDoc1",
                response.cas(),
                false,
                Observe.PersistTo.MASTER,
                Observe.ReplicateTo.NONE,
                BestEffortRetryStrategy.INSTANCE
        ).timeout(5, TimeUnit.SECONDS).toBlocking().single();

        assertTrue(observeSuccess);
    }

    @Test
    public void shouldObservePersistToMasterOnRemoval() {
        InsertRequest request = new InsertRequest("persRemDoc1", Unpooled.copiedBuffer("test", CharsetUtil.UTF_8), bucket());
        InsertResponse response = cluster().<InsertResponse>send(request).toBlocking().single();
        assertTrue(response.status().isSuccess());
        ReferenceCountUtil.release(response);

        RemoveRequest removeRequest = new RemoveRequest("persRemDoc1", bucket());
        RemoveResponse removeResponse = cluster().<RemoveResponse>send(removeRequest).toBlocking().single();
        assertTrue(removeResponse.status().isSuccess());
        ReferenceCountUtil.release(removeResponse);

        Boolean observeSuccess = Observe.call(
                cluster(),
                bucket(),
                "persRemDoc1",
                removeResponse.cas(),
                true,
                Observe.PersistTo.MASTER,
                Observe.ReplicateTo.NONE,
                BestEffortRetryStrategy.INSTANCE
        ).timeout(5, TimeUnit.SECONDS).toBlocking().single();

        assertTrue(observeSuccess);
    }

    @Test
    public void shouldObservePersistenceOnNonExistentDocRemoval() {
        Boolean observeSuccess = Observe.call(
                cluster(),
                bucket(),
                "nonExistentDoc",
                12345,
                true,
                Observe.PersistTo.MASTER,
                Observe.ReplicateTo.NONE,
                BestEffortRetryStrategy.INSTANCE
        ).timeout(5, TimeUnit.SECONDS).toBlocking().single();

        assertTrue(observeSuccess);
    }

    @Test
    public void shouldObserveReplicateToOne() {
        Assume.assumeTrue(numberOfReplicas >= 1 && numberOfNodes >= 2);

        InsertRequest request = new InsertRequest("persInsDoc2", Unpooled.copiedBuffer("test", CharsetUtil.UTF_8), bucket());
        InsertResponse response = cluster().<InsertResponse>send(request).toBlocking().single();
        assertTrue(response.status().isSuccess());
        ReferenceCountUtil.release(response);

        Boolean observeSuccess = Observe.call(
                cluster(),
                bucket(),
                "persInsDoc2",
                response.cas(),
                false,
                Observe.PersistTo.NONE,
                Observe.ReplicateTo.ONE,
                BestEffortRetryStrategy.INSTANCE
        ).timeout(5, TimeUnit.SECONDS).toBlocking().single();

        assertTrue(observeSuccess);
    }

    @Test
    public void shouldObserveReplicateToOneAndPersistToMaster() {
        Assume.assumeTrue(numberOfReplicas >= 1 && numberOfNodes >= 2);

        InsertRequest request = new InsertRequest("persInsDoc3", Unpooled.copiedBuffer("test", CharsetUtil.UTF_8), bucket());
        InsertResponse response = cluster().<InsertResponse>send(request).toBlocking().single();
        assertTrue(response.status().isSuccess());
        ReferenceCountUtil.release(response);

        Boolean observeSuccess = Observe.call(
                cluster(),
                bucket(),
                "persInsDoc3",
                response.cas(),
                false,
                Observe.PersistTo.MASTER,
                Observe.ReplicateTo.ONE,
                BestEffortRetryStrategy.INSTANCE
        ).timeout(5, TimeUnit.SECONDS).toBlocking().single();

        assertTrue(observeSuccess);
    }

    @Test
    public void shouldObservePersistToOne() {
        InsertRequest request = new InsertRequest("persInsDoc4", Unpooled.copiedBuffer("test", CharsetUtil.UTF_8), bucket());
        InsertResponse response = cluster().<InsertResponse>send(request).toBlocking().single();
        assertTrue(response.status().isSuccess());
        ReferenceCountUtil.release(response);

        Boolean observeSuccess = Observe.call(
                cluster(),
                bucket(),
                "persInsDoc4",
                response.cas(),
                false,
                Observe.PersistTo.ONE,
                Observe.ReplicateTo.NONE,
                BestEffortRetryStrategy.INSTANCE
        ).timeout(5, TimeUnit.SECONDS).toBlocking().single();

        assertTrue(observeSuccess);
    }

    @Test(expected = ReplicaNotConfiguredException.class)
    public void shouldFailReplicaIfLessReplicaConfigureOnBucket() {
        Assume.assumeTrue(numberOfReplicas < 3);

        Observe.call(
                cluster(),
                bucket(),
                "someDoc",
                1234,
                false,
                Observe.PersistTo.NONE,
                Observe.ReplicateTo.THREE,
                BestEffortRetryStrategy.INSTANCE
        ).timeout(5, TimeUnit.SECONDS).toBlocking().single();
    }

    @Test(expected = ReplicaNotConfiguredException.class)
    public void shouldFailPersistIfLessReplicaConfigureOnBucket() {
        Assume.assumeTrue(numberOfReplicas < 3);

        Observe.call(
                cluster(),
                bucket(),
                "someDoc",
                1234,
                false,
                Observe.PersistTo.FOUR,
                Observe.ReplicateTo.NONE,
                BestEffortRetryStrategy.INSTANCE
        ).timeout(5, TimeUnit.SECONDS).toBlocking().single();
    }

}
