  private boolean vBucketAware;
  private BlockingQueue<Object> rqueue;
  private HashMap<Operation, TapConnectionProvider> omap;
  private List<InetSocketAddress> addrs;
  private List<URI> baseList;
  private String bucketName;
  private String usr;
  private String pwd;
  private long messagesRead;

  public TapClient(InetSocketAddress... ia) {
    this(Arrays.asList(ia));
  }

  public TapClient(List<InetSocketAddress> addrs) {
    this.rqueue = new LinkedBlockingQueue<Object>();
    this.omap = new HashMap<Operation, TapConnectionProvider>();
    this.vBucketAware = false;
    this.addrs = addrs;
    this.baseList = null;
    this.bucketName = null;
    this.usr = null;
    this.pwd = null;
    this.messagesRead = 0;
  }

  public TapClient(final List<URI> baseList, final String bucketName,
      final String usr, final String pwd) {
    for (URI bu : baseList) {
      if (!bu.isAbsolute()) {
        throw new IllegalArgumentException("The base URI must be absolute");
      }
    }
    this.rqueue = new LinkedBlockingQueue<Object>();
    this.omap = new HashMap<Operation, TapConnectionProvider>();
    this.vBucketAware = true;
    this.addrs = null;
    this.baseList = baseList;
    this.bucketName = bucketName;
    this.usr = usr;
    this.pwd = pwd;
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

  public Operation tapCustom(String id, RequestMessage message,
      String keyFilter, String valueFilter) throws ConfigurationException,
      IOException {
    final TapConnectionProvider conn;
    if (vBucketAware) {
      conn = new TapConnectionProvider(baseList, bucketName, usr, pwd);
    } else {
      conn = new TapConnectionProvider(addrs);
    }

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

  public Operation tapBackfill(String id, final int runTime,
      final TimeUnit timeunit) throws IOException, ConfigurationException {
    return tapBackfill(id, -1, runTime, timeunit);
  }

  public Operation tapBackfill(final String id, final long date,
      final int runTime, final TimeUnit timeunit) throws IOException,
      ConfigurationException {
    final TapConnectionProvider conn;
    if (vBucketAware) {
      conn = new TapConnectionProvider(baseList, bucketName, usr, pwd);
    } else {
      conn = new TapConnectionProvider(addrs);
    }

    final CountDownLatch latch = new CountDownLatch(1);
    final Operation op = conn.getOpFactory().tapBackfill(id, date,
        new TapOperation.Callback() {
          public void receivedStatus(OperationStatus status) {
          }
