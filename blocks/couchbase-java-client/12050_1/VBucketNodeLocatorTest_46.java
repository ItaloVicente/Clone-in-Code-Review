
package com.couchbase.client.vbucket;

import java.util.List;

import junit.framework.TestCase;

import com.couchbase.client.vbucket.config.Config;
import com.couchbase.client.vbucket.config.ConfigFactory;
import com.couchbase.client.vbucket.config.DefaultConfigFactory;

public class VBucketCacheNodeLocatorTest extends TestCase {

  private static final String CONFIG_IN_ENVELOPE = "{"
      + "    \"authType\": \"sasl\", "
      + "    \"basicStats\": {"
      + "        \"diskUsed\": 0, "
      + "        \"hitRatio\": 0, "
      + "        \"itemCount\": 10001, "
      + "        \"memUsed\": 27007687, "
      + "        \"opsPerSec\": 0, "
      + "        \"quotaPercentUsed\": 10.061147436499596"
      + "    }, "
      + "    \"bucketType\": \"memcached\", "
      + "    \"flushCacheUri\": \"/pools/default/buckets/default/controller"
      + "/doFlush\", "
      + "    \"name\": \"default\", "
      + "    \"nodeLocator\": \"ketama\", "
      + "    \"nodes\": ["
      + "        {"
      + "            \"clusterCompatibility\": 1, "
      + "            \"clusterMembership\": \"active\", "
      + "            \"hostname\": \"127.0.0.1:8091\", "
      + "            \"mcdMemoryAllocated\": 2985, "
      + "            \"mcdMemoryReserved\": 2985, "
      + "            \"memoryFree\": 285800000, "
      + "            \"memoryTotal\": 3913584000.0, "
      + "            \"os\": \"i386-apple-darwin9.8.0\", "
      + "            \"ports\": {"
      + "                \"direct\": 11210, "
      + "                \"proxy\": 11211"
      + "            }, "
      + "            \"replication\": 1.0, "
      + "            \"status\": \"unhealthy\", "
      + "            \"uptime\": \"4204\", "
      + "            \"version\": \"1.6.5\""
      + "        }"
      + "    ], "
      + "    \"proxyPort\": 0, "
      + "    \"quota\": {"
      + "        \"ram\": 268435456, "
      + "        \"rawRAM\": 268435456"
      + "    }, "
      + "    \"replicaNumber\": 0, "
      + "    \"saslPassword\": \"\", "
      + "    \"stats\": {"
      + "        \"uri\": \"/pools/default/buckets/default/stats\""
      + "    }, "
      + "    \"streamingUri\": \"/pools/default/bucketsStreaming/default"
      + "\", "
      + "    \"uri\": \"/pools/default/buckets/default\"" + "}";

  public void testGetPrimary() {
  }

  public void testGetConfig() {
    ConfigFactory configFactory = new DefaultConfigFactory();
    Config config = configFactory.create(CONFIG_IN_ENVELOPE);
    config.getServersCount();
    List<String> servers = config.getServers();
    System.out.println(servers);
  }
}
