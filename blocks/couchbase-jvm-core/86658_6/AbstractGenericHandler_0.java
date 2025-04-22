
package com.couchbase.client.core.cluster;

import com.couchbase.client.core.message.internal.PingReport;
import com.couchbase.client.core.message.internal.PingServiceHealth;
import com.couchbase.client.core.service.ServiceType;
import com.couchbase.client.core.util.ClusterDependentTest;
import com.couchbase.client.core.utils.HealthPinger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class HealthPingerTest extends ClusterDependentTest {

    @BeforeClass
    public static void setup() throws Exception {
        connect();
    }

    @Test
    public void shouldPerformPingAfterConnect() {
        PingReport pr = HealthPinger.ping(
            env(),
            bucket(),
            password(),
            cluster(),
            "ping-id",
            1,
            TimeUnit.SECONDS
        ).toBlocking().value();

        assertNotNull(pr.sdk());
        assertEquals(pr.sdk(), env().userAgent());
        assertEquals("ping-id", pr.id());
        assertTrue(pr.version() > 0);
        assertTrue(pr.configRev() > 0);

        assertNotNull(pr.exportToJson());

        for (PingServiceHealth ph : pr.services()) {
            assertEquals(PingServiceHealth.PingState.OK, ph.state());
            assertTrue(ph.latency() > 0);
            assertNotNull(ph.id());
            assertNotNull(ph.remote());
            assertNotNull(ph.local());
            if (ph.type() == ServiceType.BINARY) {
                assertNotNull(ph.scope());
            } else {
                assertNull(ph.scope());
            }
        }
    }
}
