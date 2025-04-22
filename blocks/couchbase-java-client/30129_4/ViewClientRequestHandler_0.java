
package com.couchbase.client;

import com.couchbase.client.http.ViewPipelineFactory;
import com.couchbase.client.protocol.views.HttpOperation;
import com.couchbase.client.vbucket.config.Bucket;
import net.spy.memcached.compat.SpyObject;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class NettyViewConnection extends SpyObject implements ViewConnection {

  public static final int DEFAULT_CONNECT_TIMEOUT = 10;

  public static final int DEFAULT_SHUTDOWN_TIMEOUT = 60;

  private final ClientBootstrap nodeBoostrap;

  private final List<Channel> connectedChannels;

  private AtomicBoolean connecting;

  private AtomicBoolean running;

  private volatile int nextNode;

  public NettyViewConnection(List<InetSocketAddress> addrs) {
    this.connecting = new AtomicBoolean(false);
    this.running = new AtomicBoolean(false);

    this.connectedChannels = Collections.synchronizedList(
      new ArrayList<Channel>()
    );

    this.nodeBoostrap = new ClientBootstrap(
      new NioClientSocketChannelFactory(
        Executors.newCachedThreadPool(),
        Executors.newCachedThreadPool()
      )
    );
    this.nodeBoostrap.setPipelineFactory(new ViewPipelineFactory());
    bootstrapNodes(addrs);
    this.running.set(true);
  }

  private void bootstrapNodes(final List<InetSocketAddress> addrs) {
    if (!connecting.compareAndSet(false, true)) {
      getLogger().debug("Surpressing duplicate bootstrap attempt.");
      return;
    }

    final CountDownLatch connectLatch = new CountDownLatch(addrs.size());
    for (InetSocketAddress addr : addrs) {
      ChannelFuture future = nodeBoostrap.connect(addr);
      future.addListener(new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
          if (future.isSuccess()) {
            connectedChannels.add(future.getChannel());
          } else {
            getLogger().info("Could not connect to a View Node upon bootstrap.",
              future.getCause());
          }
          connectLatch.countDown();
        }
      });
    }

    try {
      connectLatch.await(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Got interrupted while waiting on View Nodes "
        + "to connect.");
    }

    if (connectedChannels.size() < addrs.size()) {
      throw new RuntimeException("Could not connect to all nodes on bootstrap.");
    }
  }

  @Override
  public void addOp(HttpOperation op) {
    if (connectedChannels.isEmpty()) {
      getLogger().error("No server connections. Cancelling op.");
      op.cancel();
    }

    Channel selected = null;
    while (selected == null) {
      int next = getNextChannelIndex();
      if (connectedChannels.get(next).isWritable()) {
        selected = connectedChannels.get(next);
      } else {
      }
    }

    selected.write(op).addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture future) throws Exception {
        if (!future.isSuccess()) {
          getLogger().warn("Could not write to View Node: " + future.getCause());
        }
      }
    });

  }

  private int getNextChannelIndex() {
    return nextNode = (++nextNode % connectedChannels.size());
  }

  @Override
  public boolean shutdown() throws IOException {
    if (!running.compareAndSet(true, false)) {
      getLogger().debug("Surpressing duplicate shutdown attempt.");
      return true;
    }

    boolean success = true;
    final CountDownLatch disconnectLatch = new CountDownLatch(
      connectedChannels.size());

    synchronized (connectedChannels) {
      Iterator<Channel> iter = connectedChannels.iterator();
      while(iter.hasNext()) {
        Channel channel = iter.next();
        channel.disconnect().addListener(new ChannelFutureListener() {
          @Override
          public void operationComplete(ChannelFuture future) throws Exception {
            if (!future.isSuccess()) {
              getLogger().info("Could not properly shut down View Node because"
                + "of: ", future.getCause());
            }
            disconnectLatch.countDown();
          }
        });
      }
    }

    try {
      disconnectLatch.await(DEFAULT_SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Got interrupted while waiting on View Nodes "
        + "to shutdown.");
    }

    nodeBoostrap.releaseExternalResources();
    return success;
  }

  @Override
  public void checkState() {
    if (!running.get()) {
      throw new IllegalStateException("Shutting down");
    }
  }

  @Override
  public void reconfigure(Bucket bucket) {
  }

  @Override
  public List<DefaultViewNode> getConnectedNodes() {
    throw new UnsupportedOperationException("remove me, I dont belong here.");
  }

  List<Channel> getConnectedChannels() {
    return connectedChannels;
  }

}
