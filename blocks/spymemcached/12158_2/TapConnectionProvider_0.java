
package net.spy.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.naming.ConfigurationException;

import net.spy.memcached.ops.Operation;
import net.spy.memcached.ops.OperationCallback;
import net.spy.memcached.ops.OperationState;
import net.spy.memcached.ops.OperationStatus;
import net.spy.memcached.ops.TapOperation;
import net.spy.memcached.tapmessage.RequestMessage;
import net.spy.memcached.tapmessage.ResponseMessage;
import net.spy.memcached.tapmessage.TapOpcode;

public class TapClient {
  protected BlockingQueue<Object> rqueue;
  protected HashMap<Operation, TapConnectionProvider> omap;
  protected long messagesRead;
  private List<InetSocketAddress> addrs;

  public TapClient(InetSocketAddress... ia) {
    this(Arrays.asList(ia));
  }

  public TapClient(List<InetSocketAddress> addrs) {
    this.rqueue = new LinkedBlockingQueue<Object>();
    this.omap = new HashMap<Operation, TapConnectionProvider>();
    this.addrs = addrs;
    this.messagesRead = 0;
  }

  public ResponseMessage getNextMessage() {
    return getNextMessage(1, TimeUnit.SECONDS);
  }

  public ResponseMessage getNextMessage(long time, TimeUnit timeunit) {
    try {
      Object m = rqueue.poll(time, timeunit);
      if (m == null) {
        return null;
      } else if (m instanceof ResponseMessage) {
        return (ResponseMessage) m;
      } else if (m instanceof TapAck) {
        TapAck ack = (TapAck) m;
        tapAck(ack.getConn(), ack.getOpcode(), ack.getOpaque(),
            ack.getCallback());
        return null;
      } else {
        throw new RuntimeException("Unexpected tap message type");
      }
    } catch (InterruptedException e) {
      shutdown();
      return null;
    }
  }

  public boolean hasMoreMessages() {
    if (!rqueue.isEmpty()) {
      return true;
    } else {
      synchronized (omap) {
        Iterator<Operation> itr = omap.keySet().iterator();
        while (itr.hasNext()) {
          Operation op = itr.next();
          if (op.getState().equals(OperationState.COMPLETE) || op.isCancelled()
              || op.hasErrored()) {
            omap.get(op).shutdown();
            omap.remove(op);
          }
        }
        if (omap.size() > 0) {
          return true;
        }
      }
    }
    return false;
  }

  public Operation tapCustom(String id, RequestMessage message)
    throws ConfigurationException, IOException {
    final TapConnectionProvider conn = new TapConnectionProvider(addrs);
    final CountDownLatch latch = new CountDownLatch(1);
    final Operation op = conn.getOpFactory().tapCustom(id, message,
        new TapOperation.Callback() {
          public void receivedStatus(OperationStatus status) {
          }

          public void gotData(ResponseMessage tapMessage) {
            rqueue.add(tapMessage);
            messagesRead++;
          }

          public void gotAck(TapOpcode opcode, int opaque) {
            rqueue.add(new TapAck(conn, opcode, opaque, this));
          }

          public void complete() {
            latch.countDown();
          }
        });
    synchronized (omap) {
      omap.put(op, conn);
    }
    conn.addOp(op);
    return op;
  }

  public Operation tapDump(final String id) throws IOException,
      ConfigurationException {
    final TapConnectionProvider conn = new TapConnectionProvider(addrs);
    final CountDownLatch latch = new CountDownLatch(1);
    final Operation op = conn.getOpFactory().tapDump(id,
        new TapOperation.Callback() {
        public void receivedStatus(OperationStatus status) {
        }
        public void gotData(ResponseMessage tapMessage) {
          rqueue.add(tapMessage);
          messagesRead++;
        }
        public void gotAck(TapOpcode opcode, int opaque) {
          rqueue.add(new TapAck(conn, opcode, opaque, this));
        }
        public void complete() {
          latch.countDown();
        }
      });
    synchronized (omap) {
      omap.put(op, conn);
    }
    conn.addOp(op);
    return op;
  }

  private void tapAck(TapConnectionProvider conn, TapOpcode opcode, int opaque,
      OperationCallback cb) {
    final Operation op = conn.getOpFactory().tapAck(opcode, opaque, cb);
    conn.addOp(op);
  }

  public void shutdown() {
    synchronized (omap) {
      for (Map.Entry<Operation, TapConnectionProvider> me : omap.entrySet()) {
        me.getValue().shutdown();
      }
    }
  }

  public long getMessagesRead() {
    return messagesRead;
  }

  class TapAck {
    private TapConnectionProvider conn;
    private TapOpcode opcode;
    private int opaque;
    private OperationCallback cb;

    public TapAck(TapConnectionProvider conn, TapOpcode opcode, int opaque,
        OperationCallback cb) {
      this.conn = conn;
      this.opcode = opcode;
      this.opaque = opaque;
      this.cb = cb;
    }

    public TapConnectionProvider getConn() {
      return conn;
    }

    public TapOpcode getOpcode() {
      return opcode;
    }

    public int getOpaque() {
      return opaque;
    }

    public OperationCallback getCallback() {
      return cb;
    }
  }
}

