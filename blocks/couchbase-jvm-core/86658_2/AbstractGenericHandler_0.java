
package com.couchbase.client.core.cluster;

import com.couchbase.client.core.service.ServiceType;
import com.couchbase.client.core.util.ClusterDependentTest;
import com.couchbase.client.core.utils.HealthPinger;
import org.junit.BeforeClass;
import org.junit.Test;
import rx.Single;

import java.util.concurrent.TimeUnit;

public class HealthPingerTest extends ClusterDependentTest {

    @BeforeClass
    public static void setup() throws Exception {
        connect();
    }

    @Test
    public void shouldPerformPingAfterConnect() {
        HealthPinger.PingReport pingResponse = HealthPinger.ping(bucket(), cluster(), 1, TimeUnit.SECONDS).toBlocking().value();

        System.err.println(pingResponse);
    }
}
