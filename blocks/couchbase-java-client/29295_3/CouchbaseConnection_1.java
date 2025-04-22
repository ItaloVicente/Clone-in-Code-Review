package com.couchbase.client;

import com.couchbase.client.vbucket.ConfigurationException;
import com.couchbase.client.vbucket.config.Bucket;
import net.spy.memcached.BroadcastOpFactory;
import net.spy.memcached.ConnectionObserver;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedConnection;
import net.spy.memcached.MemcachedNode;
import net.spy.memcached.NodeLocator;
import net.spy.memcached.OperationFactory;
import net.spy.memcached.compat.SpyObject;
import net.spy.memcached.ops.Operation;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public abstract class CouchbaseConnectableProxy<C extends CouchbaseConnectable>
  extends SpyObject implements CouchbaseConnectable {

  public static final int DEFAULT_POOL_SIZE = 1;

  private final boolean onlyOneInPool;

  private final ArrayList<C> connections;

  public CouchbaseConnectableProxy(int bufSize, CouchbaseConnectionFactory f,
    List<InetSocketAddress> a, Collection<ConnectionObserver> obs,
    FailureMode fm, OperationFactory opfactory) throws IOException {
    this(bufSize, f, a, obs, fm, opfactory, DEFAULT_POOL_SIZE);
  }

  public CouchbaseConnectableProxy(int bufSize, CouchbaseConnectionFactory f,
    List<InetSocketAddress> a, Collection<ConnectionObserver> obs,
    FailureMode fm, OperationFactory opfactory, int poolSize)
    throws IOException {
    connections = new ArrayList<C>(poolSize);

    for (int i = 0; i < poolSize; i++) {
      try {
        connections.add(getConnectableInstance(bufSize, f, a, obs, fm, opfactory));
      } catch(Exception ex) {
        getLogger().warn("Could not initialize one of the " +
          "connection pool objects.");
      }
    }

    if (connections.isEmpty()) {
      throw new ConfigurationException("Could not initialize the connection " +
        "pool objects.");
    }

    onlyOneInPool = connections.size() == 1;
  }

  abstract C getConnectableInstance(int bufSize, CouchbaseConnectionFactory f,
    List<InetSocketAddress> a, Collection<ConnectionObserver> obs,
    FailureMode fm, OperationFactory opfactory) throws IOException;

  @Override
  public void addOperation(final String key, final Operation o) {
    forOne(new ConnectionCallable<Void>() {
      @Override
      public Void call(CouchbaseConnectable c) {
        c.addOperation(key, o);
        return null;
      }
    });
  }

  @Override
  public void addOperations(final Map<MemcachedNode, Operation> ops) {
    forOne(new ConnectionCallable<Void>() {
      @Override
      public Void call(CouchbaseConnectable c) {
        c.addOperations(ops);
        return null;
      }
    });
  }

  @Override
  public void enqueueOperation(final String key, final Operation o) {
    forOne(new ConnectionCallable<Void>() {
      @Override
      public Void call(CouchbaseConnectable c) {
        c.enqueueOperation(key, o);
        return null;
      }
    });
  }

  @Override
  public void insertOperation(final MemcachedNode node, final Operation o) {
    forOne(new ConnectionCallable<Void>() {
      @Override
      public Void call(CouchbaseConnectable c) {
        c.insertOperation(node, o);
        return null;
      }
    });
  }

  @Override
  public CountDownLatch broadcastOperation(final BroadcastOpFactory of) {
    return forOneWithResult(new ConnectionCallable<CountDownLatch>() {
      @Override
      public CountDownLatch call(CouchbaseConnectable c) {
        return c.broadcastOperation(of);
      }
    });
  }

  @Override
  public CountDownLatch broadcastOperation(final BroadcastOpFactory of,
    final Collection<MemcachedNode> nodes) {
    return forOneWithResult(new ConnectionCallable<CountDownLatch>() {
      @Override
      public CountDownLatch call(CouchbaseConnectable c) {
        return c.broadcastOperation(of, nodes);
      }
    });
  }

  @Override
  public void run() {
    forAll(new ConnectionCallable<Void>() {
      @Override
      public Void call(CouchbaseConnectable c) {
        c.run();
        return null;
      }
    });
  }

  @Override
  public void handleIO() throws IOException {
    forAll(new ConnectionCallable<Void>() {
      @Override
      public Void call(CouchbaseConnectable c) {
        try {
          c.handleIO();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        return null;
      }
    });
  }

  @Override
  public boolean addObserver(final ConnectionObserver obs) {
    List<Boolean> result = forAllWithResult(new ConnectionCallable<Boolean>() {
      @Override
      public Boolean call(CouchbaseConnectable c) {
        return c.addObserver(obs);
      }
    });

    for (Boolean res : result) {
      if (!res) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean removeObserver(final ConnectionObserver obs) {
    List<Boolean> result = forAllWithResult(new ConnectionCallable<Boolean>() {
      @Override
      public Boolean call(CouchbaseConnectable c) {
        return c.removeObserver(obs);
      }
    });

    for (Boolean res : result) {
      if (!res) {
        return false;
      }
    }
    return true;
  }

  @Override
  public NodeLocator getLocator() {
    return connections.get(0).getLocator();
  }

  @Override
  public void shutdown() throws IOException {
    forAll(new ConnectionCallable<Void>() {
      @Override
      public Void call(CouchbaseConnectable c) {
        try {
          c.shutdown();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        return null;
      }
    });
  }

  @Override
  public String connectionsStatus() {
    List<String> result = forAllWithResult(new ConnectionCallable<String>() {
      @Override
      public String call(CouchbaseConnectable c) {
        return c.connectionsStatus();
      }
    });

    StringBuilder builder = new StringBuilder();
    for (String r : result) {
      builder.append(r);
      builder.append(";");
    }
    return builder.toString();
  }

  @Override
  public void reconfigure(final Bucket bucket) {
    forAll(new ConnectionCallable<Void>() {
      @Override
      public Void call(CouchbaseConnectable c) {
        c.reconfigure(bucket);
        return null;
      }
    });
  }

  private <T> List<T> forAllWithResult(ConnectionCallable<T> task) {
    try {
      List<T> results = new ArrayList<T>();
      if (onlyOneInPool) {
        results.add(task.call(connections.get(0)));
      } else {
        int connSize = connections.size();
        for (int i = 0; i < connSize; i++) {
          results.add(task.call(connections.get(i)));
        }
      }
      return results;
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  private void forAll(ConnectionCallable<Void> task) {
    try {
      if (onlyOneInPool) {
        task.call(connections.get(0));
      } else {
        int connSize = connections.size();
        for (int i = 0; i < connSize; i++) {
          task.call(connections.get(i));
        }
      }
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  private void forOne(ConnectionCallable<Void> task) {
    task.call(getNextConnectable());
  }

  private <T> T forOneWithResult(ConnectionCallable<T> task) {
    return task.call(getNextConnectable());
  }

  private CouchbaseConnectable getNextConnectable() {
    if (onlyOneInPool) {
      return connections.get(0);
    } else {
      return connections.get(0);
    }
  }

  interface ConnectionCallable<T> {
    T call(CouchbaseConnectable connectable);
  }

}
