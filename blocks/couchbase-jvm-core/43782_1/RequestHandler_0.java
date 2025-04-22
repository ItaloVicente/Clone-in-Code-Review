package com.couchbase.client.core.cluster;

import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.dcp.*;
import com.couchbase.client.core.util.ClusterDependentTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
                .<AddStreamResponse>send(new AddStreamRequest((short) 1, "default"))
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
                .<StreamRequestResponse>send(new StreamRequestRequest(0, 0, 0, 0, 0, "default"))
                .toBlocking()
                .single();
        assertEquals(ResponseStatus.SUCCESS, addStream.status());
    }
}
