
package com.couchbase.client;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.ConnectionObserver;
import net.spy.memcached.FailureMode;
import net.spy.memcached.HashAlgorithm;
import net.spy.memcached.OperationFactory;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.ops.Operation;
import net.spy.memcached.transcoders.Transcoder;
import com.couchbase.client.vbucket.config.Config;


public class CouchbaseConnectionFactoryBuilder extends ConnectionFactoryBuilder{

  private Config vBucketConfig;

  public Config getVBucketConfig() {
    return vBucketConfig;
  }

  public void setVBucketConfig(Config config) {
    this.vBucketConfig = config;
  }

  public CouchbaseConnectionFactory buildMembaseConnection(
      final List<URI> baseList, final String bucketName, final String usr,
      final String pwd) throws IOException {
    return new CouchbaseConnectionFactory(baseList, bucketName, pwd) {

      @Override
      public BlockingQueue<Operation> createOperationQueue() {
        return opQueueFactory == null ? super.createOperationQueue()
            : opQueueFactory.create();
      }

      @Override
      public BlockingQueue<Operation> createReadOperationQueue() {
        return readQueueFactory == null ? super.createReadOperationQueue()
            : readQueueFactory.create();
      }

      @Override
      public BlockingQueue<Operation> createWriteOperationQueue() {
        return writeQueueFactory == null ? super.createReadOperationQueue()
            : writeQueueFactory.create();
      }

      @Override
      public Transcoder<Object> getDefaultTranscoder() {
        return transcoder == null ? super.getDefaultTranscoder() : transcoder;
      }

      @Override
      public FailureMode getFailureMode() {
        return failureMode;
      }

      @Override
      public HashAlgorithm getHashAlg() {
        return hashAlg;
      }

      @Override
      public Collection<ConnectionObserver> getInitialObservers() {
        return initialObservers;
      }

      @Override
      public OperationFactory getOperationFactory() {
        return opFact == null ? super.getOperationFactory() : opFact;
      }

      @Override
      public long getOperationTimeout() {
        return opTimeout == -1 ? super.getOperationTimeout() : opTimeout;
      }

      @Override
      public int getReadBufSize() {
        return readBufSize == -1 ? super.getReadBufSize() : readBufSize;
      }

      @Override
      public boolean isDaemon() {
        return isDaemon;
      }

      @Override
      public boolean shouldOptimize() {
        return shouldOptimize;
      }

      @Override
      public boolean useNagleAlgorithm() {
        return useNagle;
      }

      @Override
      public long getMaxReconnectDelay() {
        return maxReconnectDelay;
      }

      @Override
      public AuthDescriptor getAuthDescriptor() {
        return authDescriptor;
      }

      @Override
      public long getOpQueueMaxBlockTime() {
        return opQueueMaxBlockTime > -1 ? opQueueMaxBlockTime
            : super.getOpQueueMaxBlockTime();
      }

      @Override
      public int getTimeoutExceptionThreshold() {
        return timeoutExceptionThreshold;
      }

    };
  }
}
