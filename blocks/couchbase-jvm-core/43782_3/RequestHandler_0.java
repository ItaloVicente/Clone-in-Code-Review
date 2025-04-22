package com.couchbase.client.core.cluster;

import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.dcp.*;
import com.couchbase.client.core.message.kv.UpsertRequest;
import com.couchbase.client.core.message.kv.UpsertResponse;
import com.couchbase.client.core.util.ClusterDependentTest;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class DCPMessageTest extends ClusterDependentTest {

    @Test
    public void shouldOpenConnection() throws Exception {
        OpenConnectionResponse single = cluster()
                .<OpenConnectionResponse>send(new OpenConnectionRequest("hello", "default"))
                .toBlocking()
                .single();
        assertEquals(ResponseStatus.SUCCESS, single.status());
    }

    @Test
    public void shouldAddStream() throws Exception {
        OpenConnectionResponse open = cluster()
                .<OpenConnectionResponse>send(new OpenConnectionRequest("hello", "default"))
                .toBlocking()
                .single();
        assertEquals(ResponseStatus.SUCCESS, open.status());

        AddStreamResponse addStream = cluster()
                .<AddStreamResponse>send(new AddStreamRequest((short) 155, "default"))
                .toBlocking()
                .single();
        assertEquals(ResponseStatus.SUCCESS, addStream.status());
        assertNotEquals(0, addStream.streamIdentifier());
    }

    @Test
    public void shouldRequestStream() throws Exception {
        OpenConnectionResponse open = cluster()
                .<OpenConnectionResponse>send(new OpenConnectionRequest("hello", "default"))
                .toBlocking()
                .single();
        assertEquals(ResponseStatus.SUCCESS, open.status());
        StreamRequestResponse addStream = cluster()
                .<StreamRequestResponse>send(new StreamRequestRequest((short) 115, "default"))
                .toBlocking()
                .single();
        assertEquals(ResponseStatus.SUCCESS, addStream.status());

        UpsertResponse foo = cluster()
                .<UpsertResponse>send(new UpsertRequest("foo", Unpooled.copiedBuffer("bar", CharsetUtil.UTF_8), 1, 0, bucket()))
                .toBlocking()
                .single();
        assertEquals(ResponseStatus.SUCCESS, foo.status());

        Thread.sleep(1100);

        Iterator<DCPRequest> items = addStream.stream().toBlocking().toIterable().iterator();
        assertTrue(items.next() instanceof SnapshotMarkerRequest);
        MutationRequest mutation = (MutationRequest) items.next();
        assertEquals("foo", mutation.key());
        assertTrue(items.next() instanceof SnapshotMarkerRequest);
        RemoveRequest remove = (RemoveRequest) items.next();
        assertEquals("foo", remove.key());
        assertFalse(items.hasNext());
    }
}
