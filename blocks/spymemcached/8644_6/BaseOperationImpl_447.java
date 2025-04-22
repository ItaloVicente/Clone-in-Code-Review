  public static final OperationStatus CANCELLED =
      new CancelledOperationStatus();
  private OperationState state = OperationState.WRITING;
  private ByteBuffer cmd = null;
  private boolean cancelled = false;
  private OperationException exception = null;
  protected OperationCallback callback = null;
  private volatile MemcachedNode handlingNode = null;
  private boolean timedout;
  private long creationTime;
  private boolean timedOutUnsent = false;
  protected Collection<MemcachedNode> notMyVbucketNodes =
      new HashSet<MemcachedNode>();

  public BaseOperationImpl() {
    super();
    creationTime = System.nanoTime();
  }

  public final OperationCallback getCallback() {
    return callback;
  }

  protected void setCallback(OperationCallback to) {
    callback = to;
  }

  public final boolean isCancelled() {
    return cancelled;
  }

  public final boolean hasErrored() {
    return exception != null;
  }

  public final OperationException getException() {
    return exception;
  }

  public final void cancel() {
    cancelled = true;
    wasCancelled();
    callback.complete();
  }

  protected void wasCancelled() {
    getLogger().debug("was cancelled.");
  }

  public final OperationState getState() {
    return state;
  }

  public final ByteBuffer getBuffer() {
    return cmd;
  }

  protected final void setBuffer(ByteBuffer to) {
    assert to != null : "Trying to set buffer to null";
    cmd = to;
    cmd.mark();
  }

  protected final void transitionState(OperationState newState) {
    getLogger().debug("Transitioned state from %s to %s", state, newState);
    state = newState;
    if (state != OperationState.WRITING) {
      cmd = null;
    }
    if (state == OperationState.COMPLETE) {
      callback.complete();
    }
    if (state == OperationState.TIMEDOUT) {
      cmd = null;
      callback.complete();
    }
  }

  public final void writeComplete() {
    transitionState(OperationState.READING);
  }

  public abstract void initialize();

  public abstract void readFromBuffer(ByteBuffer data) throws IOException;

  protected void handleError(OperationErrorType eType, String line)
    throws IOException {
    getLogger().error("Error:  %s", line);
    switch (eType) {
    case GENERAL:
      exception = new OperationException();
      break;
    case SERVER:
      exception = new OperationException(eType, line);
      break;
    case CLIENT:
      exception = new OperationException(eType, line);
      break;
    default:
      assert false;
    }
    transitionState(OperationState.COMPLETE);
    throw exception;
  }

  public void handleRead(ByteBuffer data) {
    assert false;
  }

  public MemcachedNode getHandlingNode() {
    return handlingNode;
  }

  public void setHandlingNode(MemcachedNode to) {
    handlingNode = to;
  }

  @Override
  public void timeOut() {
    assert (state != OperationState.READING || state
        != OperationState.COMPLETE);
    this.transitionState(OperationState.TIMEDOUT);
    timedout = true;
  }

  @Override
  public boolean isTimedOut() {
    return timedout;
  }

  @Override
  public boolean isTimedOut(long ttlMillis) {
    long elapsed = System.nanoTime();
    long ttlNanos = ttlMillis * 1000 * 1000;
    if (elapsed - creationTime > ttlNanos) {
      assert (state != OperationState.READING || state
          != OperationState.COMPLETE);
      this.transitionState(OperationState.TIMEDOUT);
      timedOutUnsent = true;
      timedout = true;
    } else {
      if (timedout) {
        throw new IllegalArgumentException("Operation has already timed out;"
            + " ttl specified would allow it to be valid.");
      }
    }
    return timedout;
  }

  @Override
  public boolean isTimedOutUnsent() {
    return timedOutUnsent;
  }
