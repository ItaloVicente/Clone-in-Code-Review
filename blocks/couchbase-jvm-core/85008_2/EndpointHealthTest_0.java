
package com.couchbase.client.core.message.internal;

import com.couchbase.client.core.service.ServiceType;
import com.couchbase.client.core.state.LifecycleState;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EndpointHealthTest {

    @Test
    public void shouldAllowInitOfLocalAndRemoteWithNull() {
        EndpointHealth eh = new EndpointHealth(
            ServiceType.QUERY,
            LifecycleState.CONNECTED,
            null,
            null,
            0,
            0
        );

        assertNull(eh.local());
        assertNull(eh.remote());

        Map<String, Object> result = eh.toMap();
        assertEquals(0L, result.get("latency_us"));
        assertEquals(0L, result.get("last_activity_us"));
        assertEquals("connected", result.get("state"));
        assertEquals("", result.get("remote"));
        assertEquals("", result.get("local"));
    }

}
