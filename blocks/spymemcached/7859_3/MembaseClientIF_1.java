package net.spy.memcached;

import java.io.IOException;
import java.net.URI;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedSelectorException;
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
import net.spy.memcached.vbucket.ConfigurationException;
import net.spy.memcached.vbucket.Reconfigurable;
import net.spy.memcached.vbucket.config.Bucket;

public class MembaseClient extends MemcachedClient implements MembaseClientIF, Reconfigurable {

	private volatile boolean reconfiguring = false;

	public MembaseClient(List<URI> baseList, String bucketName, String pwd)
			throws IOException, ConfigurationException {
		this(new MembaseConnectionFactory(baseList, bucketName, bucketName, pwd));
	}

	public MembaseClient(final List<URI> baseList, final String bucketName,
		final String usr, final String pwd) throws IOException, ConfigurationException {
		this(new MembaseConnectionFactory(baseList, bucketName, usr, pwd));
	}

	public MembaseClient(MembaseConnectionFactory cf) throws IOException, ConfigurationException {
		super(cf, AddrUtil.getAddresses(cf.getVBucketConfig().getServers()));
	}

	public void reconfigure(Bucket bucket) {
		reconfiguring = true;
		try {
			conn.reconfigure(bucket);
		} catch (IllegalArgumentException ex) {
			getLogger().warn("Failed to reconfigure client, staying with previous configuration.", ex);
		} finally {
			reconfiguring = false;
		}
	}

	public <T> OperationFuture<CASValue<T>> asyncGetAndLock(final String key, int exp,
			final Transcoder<T> tc) {
		final CountDownLatch latch=new CountDownLatch(1);
		final OperationFuture<CASValue<T>> rv=
			new OperationFuture<CASValue<T>>(key, latch, operationTimeout);

		Operation op=opFact.getl(key, exp,
				new GetlOperation.Callback() {
			private CASValue<T> val=null;
			public void receivedStatus(OperationStatus status) {
				rv.set(val, status);
			}
			public void gotData(String k, int flags, long cas, byte[] data) {
				assert key.equals(k) : "Wrong key returned";
				assert cas > 0 : "CAS was less than zero:  " + cas;
				val=new CASValue<T>(cas, tc.decode(
					new CachedData(flags, data, tc.getMaxSize())));
			}
			public void complete() {
				latch.countDown();
			}});
		rv.setOperation(op);
		addOp(key, op);
		return rv;
	}

	public OperationFuture<CASValue<Object>> asyncGetAndLock(final String key, int exp) {
		return asyncGetAndLock(key, exp, transcoder);
	}

	public <T> CASValue<T> getAndLock(String key, int exp, Transcoder<T> tc) {
		try {
			return asyncGetAndLock(key, exp, tc).get(
					operationTimeout, TimeUnit.MILLISECONDS);
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
	public void run() {
		while(running) {
            if (!reconfiguring) {
                try {
                    conn.handleIO();
                } catch (IOException e) {
                    logRunException(e);
                } catch (CancelledKeyException e) {
                    logRunException(e);
                } catch (ClosedSelectorException e) {
                    logRunException(e);
                } catch (IllegalStateException e) {
                    logRunException(e);
                }
			}
		}
		getLogger().info("Shut down memcached client");
	}

	@Override
	public boolean shutdown(long timeout, TimeUnit unit) {
		boolean shutdownResult = super.shutdown(timeout, unit);
		MembaseConnectionFactory cf = (MembaseConnectionFactory)connFactory;
		if (cf.getConfigurationProvider() != null) {
			cf.getConfigurationProvider().shutdown();
        }
		return shutdownResult;
	}
}
