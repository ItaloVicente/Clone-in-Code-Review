public abstract class TCPMemcachedNodeImpl extends SpyObject implements
    MemcachedNode {

  private final SocketAddress socketAddress;
  private final ByteBuffer rbuf;
  private final ByteBuffer wbuf;
  protected final BlockingQueue<Operation> writeQ;
  private final BlockingQueue<Operation> readQ;
  private final BlockingQueue<Operation> inputQueue;
  private final long opQueueMaxBlockTime;
  private volatile int reconnectAttempt = 1;
  private SocketChannel channel;
  private int toWrite = 0;
  protected Operation optimizedOp = null;
  private volatile SelectionKey sk = null;
  private boolean shouldAuth = false;
  private CountDownLatch authLatch;
  private ArrayList<Operation> reconnectBlocked;
  private long defaultOpTimeout;

  private final AtomicInteger continuousTimeout = new AtomicInteger(0);

  public TCPMemcachedNodeImpl(SocketAddress sa, SocketChannel c, int bufSize,
      BlockingQueue<Operation> rq, BlockingQueue<Operation> wq,
      BlockingQueue<Operation> iq, long opQueueMaxBlockTime,
      boolean waitForAuth, long dt) {
    super();
    assert sa != null : "No SocketAddress";
    assert c != null : "No SocketChannel";
    assert bufSize > 0 : "Invalid buffer size: " + bufSize;
    assert rq != null : "No operation read queue";
    assert wq != null : "No operation write queue";
    assert iq != null : "No input queue";
    socketAddress = sa;
    setChannel(c);
    rbuf = ByteBuffer.allocate(bufSize);
    wbuf = ByteBuffer.allocate(bufSize);
    getWbuf().clear();
    readQ = rq;
    writeQ = wq;
    inputQueue = iq;
    this.opQueueMaxBlockTime = opQueueMaxBlockTime;
    shouldAuth = waitForAuth;
    defaultOpTimeout = dt;
    setupForAuth();
  }

  public final void copyInputQueue() {
    Collection<Operation> tmp = new ArrayList<Operation>();

    inputQueue.drainTo(tmp, writeQ.remainingCapacity());

    writeQ.addAll(tmp);
  }

  public Collection<Operation> destroyInputQueue() {
    Collection<Operation> rv = new ArrayList<Operation>();
    inputQueue.drainTo(rv);
    return rv;
  }

  public final void setupResend() {
    Operation op = getCurrentWriteOp();
    if (shouldAuth && op != null) {
      op.cancel();
    } else if (op != null) {
      ByteBuffer buf = op.getBuffer();
      if (buf != null) {
        buf.reset();
      } else {
        getLogger().info("No buffer for current write op, removing");
        removeCurrentWriteOp();
      }
    }
    while (hasReadOp()) {
      op = removeCurrentReadOp();
      if (op != getCurrentWriteOp()) {
        getLogger().warn("Discarding partially completed op: %s", op);
        op.cancel();
      }
    }

    while (shouldAuth && hasWriteOp()) {
      op = removeCurrentWriteOp();
      getLogger().warn("Discarding partially completed op: %s", op);
      op.cancel();
    }

    getWbuf().clear();
    getRbuf().clear();
    toWrite = 0;
  }

  private boolean preparePending() {
    copyInputQueue();

    Operation nextOp = getCurrentWriteOp();
    while (nextOp != null && nextOp.isCancelled()) {
      getLogger().info("Removing cancelled operation: %s", nextOp);
      removeCurrentWriteOp();
      nextOp = getCurrentWriteOp();
    }
    return nextOp != null;
  }

  public final void fillWriteBuffer(boolean shouldOptimize) {
    if (toWrite == 0 && readQ.remainingCapacity() > 0) {
      getWbuf().clear();
      Operation o = getCurrentWriteOp();
      if (o != null && (o.isCancelled())) {
        getLogger().debug("Not writing cancelled op.");
        Operation cancelledOp = removeCurrentWriteOp();
        assert o == cancelledOp;
        return;
      }
      if (o != null && o.isTimedOut(defaultOpTimeout)) {
        getLogger().debug("Not writing timed out op.");
        Operation timedOutOp = removeCurrentWriteOp();
        assert o == timedOutOp;
        return;
      }
      while (o != null && toWrite < getWbuf().capacity()) {
        assert o.getState() == OperationState.WRITING;
        if (!readQ.contains(o) && !(o instanceof TapAckOperationImpl)) {
          readQ.add(o);
        }

        ByteBuffer obuf = o.getBuffer();
        assert obuf != null : "Didn't get a write buffer from " + o;
        int bytesToCopy = Math.min(getWbuf().remaining(), obuf.remaining());
        byte[] b = new byte[bytesToCopy];
        obuf.get(b);
        getWbuf().put(b);
        getLogger().debug("After copying stuff from %s: %s", o, getWbuf());
        if (!o.getBuffer().hasRemaining()) {
          o.writeComplete();
          transitionWriteItem();

          preparePending();
          if (shouldOptimize) {
            optimize();
          }

          o = getCurrentWriteOp();
        }
        toWrite += bytesToCopy;
      }
      getWbuf().flip();
      assert toWrite <= getWbuf().capacity() : "toWrite exceeded capacity: "
          + this;
      assert toWrite == getWbuf().remaining() : "Expected " + toWrite
          + " remaining, got " + getWbuf().remaining();
    } else {
      getLogger().debug("Buffer is full, skipping");
    }
  }

  public final void transitionWriteItem() {
    Operation op = removeCurrentWriteOp();
    assert op != null : "There is no write item to transition";
    getLogger().debug("Finished writing %s", op);
  }

  protected abstract void optimize();

  public final Operation getCurrentReadOp() {
    return readQ.peek();
  }

  public final Operation removeCurrentReadOp() {
    return readQ.remove();
  }

  public final Operation getCurrentWriteOp() {
    return optimizedOp == null ? writeQ.peek() : optimizedOp;
  }

  public final Operation removeCurrentWriteOp() {
    Operation rv = optimizedOp;
    if (rv == null) {
      rv = writeQ.remove();
    } else {
      optimizedOp = null;
    }
    return rv;
  }

  public final boolean hasReadOp() {
    return !readQ.isEmpty();
  }

  public final boolean hasWriteOp() {
    return !(optimizedOp == null && writeQ.isEmpty());
  }

  public final void addOp(Operation op) {
    try {
      if (!authLatch.await(1, TimeUnit.SECONDS)) {
        op.cancel();
        getLogger().warn(
            "Operation canceled because authentication "
                + "or reconnection and authentication has "
                + "taken more than one second to complete.");
        getLogger().debug("Canceled operation %s", op.toString());
        return;
      }
      if (!inputQueue.offer(op, opQueueMaxBlockTime, TimeUnit.MILLISECONDS)) {
        throw new IllegalStateException("Timed out waiting to add " + op
            + "(max wait=" + opQueueMaxBlockTime + "ms)");
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("Interrupted while waiting to add " + op);
    }
  }

  public final void insertOp(Operation op) {
    ArrayList<Operation> tmp = new ArrayList<Operation>(inputQueue.size() + 1);
    tmp.add(op);
    inputQueue.drainTo(tmp);
    inputQueue.addAll(tmp);
  }

  public final int getSelectionOps() {
    int rv = 0;
    if (getChannel().isConnected()) {
      if (hasReadOp()) {
        rv |= SelectionKey.OP_READ;
      }
      if (toWrite > 0 || hasWriteOp()) {
        rv |= SelectionKey.OP_WRITE;
      }
    } else {
      rv = SelectionKey.OP_CONNECT;
    }
    return rv;
  }

  public final ByteBuffer getRbuf() {
    return rbuf;
  }

  public final ByteBuffer getWbuf() {
    return wbuf;
  }

  public final SocketAddress getSocketAddress() {
    return socketAddress;
  }

  public final boolean isActive() {
    return reconnectAttempt == 0 && getChannel() != null
        && getChannel().isConnected();
  }

  public final void reconnecting() {
    reconnectAttempt++;
    continuousTimeout.set(0);
  }

  public final void connected() {
    reconnectAttempt = 0;
    continuousTimeout.set(0);
  }

  public final int getReconnectCount() {
    return reconnectAttempt;
  }

  @Override
  public final String toString() {
    int sops = 0;
    if (getSk() != null && getSk().isValid()) {
      sops = getSk().interestOps();
    }
    int rsize = readQ.size() + (optimizedOp == null ? 0 : 1);
    int wsize = writeQ.size();
    int isize = inputQueue.size();
    return "{QA sa=" + getSocketAddress() + ", #Rops=" + rsize + ", #Wops="
        + wsize + ", #iq=" + isize + ", topRop=" + getCurrentReadOp()
        + ", topWop=" + getCurrentWriteOp() + ", toWrite=" + toWrite
        + ", interested=" + sops + "}";
  }

  public final void registerChannel(SocketChannel ch, SelectionKey skey) {
    setChannel(ch);
    setSk(skey);
  }

  public final void setChannel(SocketChannel to) {
    assert channel == null || !channel.isOpen()
      : "Attempting to overwrite channel";
    channel = to;
  }

  public final SocketChannel getChannel() {
    return channel;
  }

  public final void setSk(SelectionKey to) {
    sk = to;
  }

  public final SelectionKey getSk() {
    return sk;
  }

  public final int getBytesRemainingToWrite() {
    return toWrite;
  }

  public final int writeSome() throws IOException {
    int wrote = channel.write(wbuf);
    assert wrote >= 0 : "Wrote negative bytes?";
    toWrite -= wrote;
    assert toWrite >= 0 : "toWrite went negative after writing " + wrote
        + " bytes for " + this;
    getLogger().debug("Wrote %d bytes", wrote);
    return wrote;
  }

  public void setContinuousTimeout(boolean timedOut) {
    if (timedOut && isActive()) {
      continuousTimeout.incrementAndGet();
    } else {
      continuousTimeout.set(0);
    }
  }

  public int getContinuousTimeout() {
    return continuousTimeout.get();
  }

  public final void fixupOps() {
    SelectionKey s = sk;
    if (s != null && s.isValid()) {
      int iops = getSelectionOps();
      getLogger().debug("Setting interested opts to %d", iops);
      s.interestOps(iops);
    } else {
      getLogger().debug("Selection key is not valid.");
    }
  }

  public final void authComplete() {
    if (reconnectBlocked != null && reconnectBlocked.size() > 0) {
      inputQueue.addAll(reconnectBlocked);
    }
    authLatch.countDown();
  }

  public final void setupForAuth() {
    if (shouldAuth) {
      authLatch = new CountDownLatch(1);
      if (inputQueue.size() > 0) {
        reconnectBlocked = new ArrayList<Operation>(inputQueue.size() + 1);
        inputQueue.drainTo(reconnectBlocked);
      }
      assert (inputQueue.size() == 0);
      setupResend();
    } else {
      authLatch = new CountDownLatch(0);
    }
  }
