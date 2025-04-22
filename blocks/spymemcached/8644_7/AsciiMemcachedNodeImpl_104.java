  public AsciiMemcachedNodeImpl(SocketAddress sa, SocketChannel c, int bufSize,
      BlockingQueue<Operation> rq, BlockingQueue<Operation> wq,
      BlockingQueue<Operation> iq, Long opQueueMaxBlockTimeNs, long dt) {
    super(sa, c, bufSize, rq, wq, iq, opQueueMaxBlockTimeNs, false, dt);
  }

  @Override
  protected void optimize() {
    if (writeQ.peek() instanceof GetOperation) {
      optimizedOp = writeQ.remove();
      if (writeQ.peek() instanceof GetOperation) {
        OptimizedGetImpl og = new OptimizedGetImpl((GetOperation) optimizedOp);
        optimizedOp = og;

        while (writeQ.peek() instanceof GetOperation) {
          GetOperationImpl o = (GetOperationImpl) writeQ.remove();
          if (!o.isCancelled()) {
            og.addOperation(o);
          }
        }
