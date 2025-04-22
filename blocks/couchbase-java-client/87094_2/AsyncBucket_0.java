package com.couchbase.client.java;

import com.couchbase.client.core.message.internal.DiagnosticsReport;
import com.couchbase.client.core.message.internal.EndpointHealth;
import com.couchbase.client.core.message.internal.PingReport;
import com.couchbase.client.core.message.internal.PingServiceHealth;
import com.couchbase.client.core.service.ServiceType;
import com.couchbase.client.core.state.LifecycleState;
import com.couchbase.client.java.bucket.BucketType;
import com.couchbase.client.java.util.CouchbaseTestContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DiagnosticsTest {

  private static CouchbaseTestContext ctx;

  @BeforeClass
  public static void connect() {
    ctx = CouchbaseTestContext.builder()
      .bucketQuota(256)
      .bucketType(BucketType.COUCHBASE)
      .build();
  }

  @AfterClass
  public static void disconnect() {
    ctx.disconnect();
  }

  @Test
  public void shouldRunDiagnostics() {
    DiagnosticsReport sh = ctx.cluster().diagnostics("myReportId");

    assertNotNull(sh);

    List<EndpointHealth> eph = sh.endpoints();
    assertFalse(eph.isEmpty());

    for (EndpointHealth eh : eph) {
      assertNotNull(eh.type());
      assertEquals(LifecycleState.CONNECTED, eh.state());
      assertNotNull(eh.local());
      assertNotNull(eh.remote());
      assertTrue(eh.lastActivity() > 0);
      assertTrue(eh.id().startsWith("0x"));
    }

    assertNotNull(sh.sdk());
    assertEquals(sh.sdk(), ctx.env().userAgent());
    assertEquals("myReportId", sh.id());

    assertNotNull(sh.exportToJson());
  }

  @Test
  public void shouldRunPing() {
    PingReport pr = ctx.bucket().ping("myReportId");

    assertNotNull(pr.sdk());
    assertEquals(pr.sdk(), ctx.env().userAgent());
    assertEquals("myReportId", pr.id());
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
