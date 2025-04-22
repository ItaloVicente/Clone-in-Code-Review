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

    if (runTime > 0) {
      Runnable r = new Runnable() {
        @Override
        public void run() {
          try {
            Thread.sleep(TimeUnit.MILLISECONDS.convert(runTime, timeunit));
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
          conn.shutdown();
          synchronized (omap) {
            omap.remove(op);
          }
        }
      };
      new Thread(r).start();
    }
    return op;
  }

  public Operation tapDump(final String id) throws IOException,
      ConfigurationException {
    final TapConnectionProvider conn;
    if (vBucketAware) {
      conn = new TapConnectionProvider(baseList, bucketName, usr, pwd);
    } else {
      conn = new TapConnectionProvider(addrs);
    }

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
