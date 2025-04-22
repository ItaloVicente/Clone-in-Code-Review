
package net.spy.memcached.auth;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedConnection;
import net.spy.memcached.MemcachedNode;
import net.spy.memcached.OperationFactory;
import net.spy.memcached.TestConfig;

import org.junit.Before;
import org.junit.Test;

import com.couchbase.client.BucketTool;
import com.couchbase.client.BucketTool.FunctionCallback;
import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;
import com.couchbase.client.clustermanager.BucketType;


public class AuthTest {

  private static CouchbaseClient saslClient;

  CouchbaseConnectionFactory cf;

  private final List<URI> uris = Arrays.asList(URI.create("http://"
        + TestConfig.IPV4_ADDR + ":8091/pools"));

  @Before
  public void initClient() throws Exception {
    BucketTool bucketTool = new BucketTool();
    bucketTool.deleteAllBuckets();
    bucketTool.createDefaultBucket(BucketType.COUCHBASE, 256, 1, true);
    bucketTool.createSaslBucket("SaslBucket", BucketType.COUCHBASE, 256, 1, true);

    BucketTool.FunctionCallback callback = new FunctionCallback() {
      @Override
      public void callback() throws Exception {
    	cf = new CouchbaseConnectionFactory(uris, "SaslBucket", "SaslBucket");
        initClient(cf);
      }

      @Override
      public String success(long elapsedTime) {
        return "Client Initialization took " + elapsedTime + "ms";
      }
    };
    bucketTool.poll(callback);
    bucketTool.waitForWarmup(saslClient);
  }

  protected void initClient(CouchbaseConnectionFactory cf) throws Exception {
	  saslClient = new CouchbaseClient(cf);
  }

  @Test
  public void testAuthMech() throws Exception {
    double serverVersion = getServerVersion();
    String authMechanism = saslClient.listSaslMechanisms().toString();
    if(serverVersion>=2.2){
      assertEquals(authMechanism,"[PLAIN, CRAM-MD5]");
    }else{
      assertEquals(authMechanism,"[PLAIN]");
    }
  }

  @Test
  public void testAuthThread() throws Exception {
    AuthThreadMonitor atm = new AuthThreadMonitor();
	OperationFactory opFact = cf.getOperationFactory();
    assert opFact != null : "Connection factory failed to make op factory";
    MemcachedConnection mconn =
    	      cf.createConnection(AddrUtil.getAddresses(cf.getVBucketConfig().getServers()));
    assert mconn != null : "Connection factory failed to make a connection";
    AuthDescriptor authDescriptor = cf.getAuthDescriptor();
    MemcachedNode node = mconn.getLocator().getPrimary(TestConfig.IPV4_ADDR);
	atm.authConnection(mconn, opFact, authDescriptor , node);
	String[] saslMechs = AuthThreadMonitor.saslMechs;
	double serverVersion = getServerVersion();
    if(serverVersion>=2.2){
      assertEquals(saslMechs[0],"[CRAM-MD5]");
    }else{
      assertEquals(saslMechs[0],"[PLAIN]");
    }
  }

  private double getServerVersion() {
    String versionString =
      (saslClient.getVersions().toString().split("=")[1]).split("_")[0];
    double serverVersion =
      Double.valueOf(versionString.substring(0,versionString.lastIndexOf('.')));
    return serverVersion;
  }
}
