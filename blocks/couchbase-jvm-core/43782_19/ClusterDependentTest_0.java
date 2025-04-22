package com.couchbase.client.core.cluster;

import com.couchbase.client.core.config.CouchbaseBucketConfig;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.cluster.GetClusterConfigRequest;
import com.couchbase.client.core.message.cluster.GetClusterConfigResponse;
import com.couchbase.client.core.message.dcp.DCPRequest;
import com.couchbase.client.core.message.dcp.MutationMessage;
import com.couchbase.client.core.message.dcp.OpenConnectionRequest;
import com.couchbase.client.core.message.dcp.OpenConnectionResponse;
import com.couchbase.client.core.message.dcp.RemoveMessage;
import com.couchbase.client.core.message.dcp.SnapshotMarkerMessage;
import com.couchbase.client.core.message.dcp.StreamRequestRequest;
import com.couchbase.client.core.message.dcp.StreamRequestResponse;
import com.couchbase.client.core.message.kv.UpsertRequest;
import com.couchbase.client.core.message.kv.UpsertResponse;
import com.couchbase.client.core.util.ClusterDependentTest;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import rx.functions.Action1;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DCPMessageTest extends ClusterDependentTest {
    @Before
    public void checkIfDCPEnabled() {
        Assume.assumeTrue(env().dcpEnabled());
    }

    @Test
    public void shouldOpenConnection() throws Exception {
        List<OpenConnectionResponse> open = cluster()
                .<OpenConnectionResponse>send(new OpenConnectionRequest("hello", bucket()))
                .toList()
                .toBlocking()
                .single();
        for (OpenConnectionResponse response : open) {
            assertEquals(ResponseStatus.SUCCESS, response.status());
        }
    }

    @Test
    public void shouldRequestStream() throws Exception {
        List<OpenConnectionResponse> open = cluster()
                .<OpenConnectionResponse>send(new OpenConnectionRequest("hello", bucket()))
                .toList()
                .toBlocking()
                .single();
        for (OpenConnectionResponse response : open) {
            assertEquals(ResponseStatus.SUCCESS, response.status());
        }
        StreamRequestResponse addStream = cluster()
                .<StreamRequestResponse>send(new StreamRequestRequest(calculateVBucketForKey("foo"), bucket()))
                .toBlocking()
                .single();
        assertEquals(ResponseStatus.SUCCESS, addStream.status());

        final List<DCPRequest> items = new ArrayList<DCPRequest>();
        addStream.stream().subscribe(new Action1<DCPRequest>() {
            @Override
            public void call(DCPRequest dcpRequest) {
                items.add(dcpRequest);
            }
        });

        UpsertResponse foo = cluster()
                .<UpsertResponse>send(new UpsertRequest("foo", Unpooled.copiedBuffer("bar", CharsetUtil.UTF_8), 1, 0, bucket()))
                .toBlocking()
                .single();
        assertEquals(ResponseStatus.SUCCESS, foo.status());

        Thread.sleep(1100);
        List<DCPRequest> rest = addStream.stream()
                .take(Math.max(0, 4 - items.size()))
                .toList().toBlocking().single();
        items.addAll(rest);

        assertEquals(4, items.size());
        assertTrue(items.get(0) instanceof SnapshotMarkerMessage);
        MutationMessage mutation = (MutationMessage) items.get(1);
        assertEquals("foo", mutation.key());
        assertTrue(items.get(2) instanceof SnapshotMarkerMessage);
        RemoveMessage remove = (RemoveMessage) items.get(3);
        assertEquals("foo", remove.key());
    }

    private short calculateVBucketForKey(String key) {
        GetClusterConfigResponse res = cluster()
                .<GetClusterConfigResponse>send(new GetClusterConfigRequest()).toBlocking().single();
        CouchbaseBucketConfig config = (CouchbaseBucketConfig) res.config().bucketConfig(bucket());
        CRC32 crc32 = new CRC32();
        try {
            crc32.update(key.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        long rv = (crc32.getValue() >> 16) & 0x7fff;
        return (short) ((int) rv & config.partitions().size() - 1);
    }
}
