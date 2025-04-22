
package net.spy.memcached.auth;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.Arrays;

import net.spy.memcached.TestConfig;

import org.junit.Test;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;


public class AuthTest {

  private CouchbaseClient client;

  protected void initClient() throws Exception {
    CouchbaseConnectionFactory cf = new CouchbaseConnectionFactory(Arrays.asList(
      URI.create("http://" + TestConfig.IPV4_ADDR + ":8091/pools")), "default",
      "");
    client = new CouchbaseClient((CouchbaseConnectionFactory) cf);
  }

  @Test
  public void testAuthMech() throws Exception {
    initClient();
    String serverVersion = client.getVersions().toString();
    String authMechanism = client.listSaslMechanisms().toString();
    if(serverVersion.contains("2.0.1")){
      assertEquals(authMechanism,"[PLAIN]");
    }
    if(serverVersion.contains("2.2") || serverVersion.contains("2.5")){
      assertEquals(authMechanism,"[PLAIN, CRAM-MD5]");
    }
  }
}
