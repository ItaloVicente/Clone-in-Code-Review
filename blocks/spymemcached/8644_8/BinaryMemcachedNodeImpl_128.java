  public BinaryMemcachedNodeImpl(SocketAddress sa, SocketChannel c,
      int bufSize, BlockingQueue<Operation> rq, BlockingQueue<Operation> wq,
      BlockingQueue<Operation> iq, Long opQueueMaxBlockTimeNs,
      boolean waitForAuth, long dt) {
    super(sa, c, bufSize, rq, wq, iq, opQueueMaxBlockTimeNs, waitForAuth, dt);
  }
