
package com.couchbase.client;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.spy.memcached.internal.OperationFuture;
import net.spy.memcached.ops.GetlOperation;
import net.spy.memcached.ops.Operation;
import net.spy.memcached.ops.OperationStatus;
import net.spy.memcached.transcoders.Transcoder;
import com.couchbase.client.vbucket.Reconfigurable;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.CASValue;
import net.spy.memcached.CachedData;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.OperationTimeoutException;
import com.couchbase.client.vbucket.config.Bucket;

public class CouchbaseClient extends MemcachedClient implements CouchbaseClientIF,
    Reconfigurable {

  protected volatile boolean reconfiguring = false;

  public CouchbaseClient(List<URI> baseList, String bucketName, String pwd)
    throws IOException {
    this(new CouchbaseConnectionFactory(baseList, bucketName, pwd));
  }

  public CouchbaseClient(final List<URI> baseList, final String bucketName,
      final String usr, final String pwd) throws IOException {
    this(new CouchbaseConnectionFactory(baseList, bucketName, pwd));
  }

  public CouchbaseClient(CouchbaseConnectionFactory cf) throws IOException {
    this(cf, true);
  }

  protected CouchbaseClient(CouchbaseConnectionFactory cf, boolean subscribe)
    throws IOException {
    super(cf, AddrUtil.getAddresses(cf.getVBucketConfig().getServers()));
    if (subscribe) {
      cf.getConfigurationProvider().subscribe(cf.getBucketName(), this);
    }
  }

  public void reconfigure(Bucket bucket) {
    reconfiguring = true;
    try {
      ((CouchbaseConnection)mconn).reconfigure(bucket);
    } catch (IllegalArgumentException ex) {
      getLogger().warn(
          "Failed to reconfigure client, staying with previous configuration.",
          ex);
    } finally {
      reconfiguring = false;
    }
  }

  public <T> OperationFuture<CASValue<T>> asyncGetAndLock(final String key,
      int exp, final Transcoder<T> tc) {
    final CountDownLatch latch = new CountDownLatch(1);
    final OperationFuture<CASValue<T>> rv =
        new OperationFuture<CASValue<T>>(key, latch, operationTimeout);

    Operation op = opFact.getl(key, exp, new GetlOperation.Callback() {
      private CASValue<T> val = null;

      public void receivedStatus(OperationStatus status) {
        if (!status.isSuccess()) {
          val = new CASValue<T>(-1, null);
        }
        rv.set(val, status);
      }

      public void gotData(String k, int flags, long cas, byte[] data) {
        assert key.equals(k) : "Wrong key returned";
        assert cas > 0 : "CAS was less than zero:  " + cas;
        val =
            new CASValue<T>(cas, tc.decode(new CachedData(flags, data, tc
                .getMaxSize())));
      }

      public void complete() {
        latch.countDown();
      }
    });
    rv.setOperation(op);
    addOp(key, op);
    return rv;
  }

  public OperationFuture<CASValue<Object>> asyncGetAndLock(final String key,
      int exp) {
    return asyncGetAndLock(key, exp, transcoder);
  }

  public <T> CASValue<T> getAndLock(String key, int exp, Transcoder<T> tc) {
    try {
      return asyncGetAndLock(key, exp, tc).get(operationTimeout,
          TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for value", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Exception waiting for value", e);
    } catch (TimeoutException e) {
      throw new OperationTimeoutException("Timeout waiting for value", e);
    }
  }

  public CASValue<Object> getAndLock(String key, int exp) {
    return getAndLock(key, exp, transcoder);
  }

  @Override
  public int getNumVBuckets() {
    return ((CouchbaseConnectionFactory)connFactory).getVBucketConfig()
      .getVbucketsCount();
  }

  @Override
  public boolean shutdown(long timeout, TimeUnit unit) {
    boolean shutdownResult = super.shutdown(timeout, unit);
    CouchbaseConnectionFactory cf = (CouchbaseConnectionFactory) connFactory;
    if (cf.getConfigurationProvider() != null) {
      cf.getConfigurationProvider().shutdown();
    }
    return shutdownResult;
  }
}
