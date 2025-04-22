
package net.spy.memcached;

import net.spy.memcached.metrics.DefaultMetricCollector;
import net.spy.memcached.metrics.DummyMetricCollector;
import net.spy.memcached.metrics.MetricType;
import net.spy.memcached.metrics.NoopMetricCollector;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

public class MetricsTest {

  @Test
  public void isDisabledByDefault() throws Exception {
    System.clearProperty("net.spy.metrics.enable");
    System.clearProperty("net.spy.metrics.type");

    ConnectionFactory cf = new DefaultConnectionFactory();
    assertEquals(MetricType.OFF, cf.enableMetrics());
    assertTrue(cf.getMetricCollector() instanceof NoopMetricCollector);

    cf = new BinaryConnectionFactory();
    assertEquals(MetricType.OFF, cf.enableMetrics());
    assertTrue(cf.getMetricCollector() instanceof NoopMetricCollector);
  }

  @Test
  public void canBeEnabledManually() throws Exception {
    ConnectionFactory cf = new ConnectionFactoryBuilder()
      .setEnableMetrics(MetricType.PERFORMANCE)
      .build();

    assertEquals(MetricType.PERFORMANCE, cf.enableMetrics());
    assertTrue(cf.getMetricCollector() instanceof DefaultMetricCollector);

    System.setProperty("net.spy.metrics.enable", "true");
    System.setProperty("net.spy.metrics.type", "performance");
    cf = new ConnectionFactoryBuilder().build();
    assertEquals(MetricType.PERFORMANCE, cf.enableMetrics());
    assertTrue(cf.getMetricCollector() instanceof DefaultMetricCollector);
  }

  @Test
  public void doStuff() throws Exception {
    DummyMetricCollector collector = new DummyMetricCollector();
    ConnectionFactory cf = new ConnectionFactoryBuilder()
      .setEnableMetrics(MetricType.DEBUG)
      .setMetricCollector(collector)
      .build();

    MemcachedClient client = new MemcachedClient(cf,
      AddrUtil.getAddresses(TestConfig.IPV4_ADDR + ":"
        + TestConfig.PORT_NUMBER));

    assertTrue(client.set("metrics:test", 0, "value").get());
    assertNotNull(client.get("metrics:test"));

    HashMap<String, Integer> metrics = collector.getMetrics();
    assertTrue(metrics.get("[MEM] Average Bytes written to OS per write") > 0);
    assertEquals(2, (long) metrics.get("[MEM] Response Rate: Success"));

    client.shutdown();
  }

}
