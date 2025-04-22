  boolean isCancelled();

  boolean hasErrored();

  OperationException getException();

  OperationCallback getCallback();

  void cancel();

  OperationState getState();

  ByteBuffer getBuffer();

  void writeComplete();

  void initialize();

  void readFromBuffer(ByteBuffer data) throws IOException;

  void handleRead(ByteBuffer data);

  MemcachedNode getHandlingNode();

  void setHandlingNode(MemcachedNode to);

  void timeOut();

  boolean isTimedOut();

  boolean isTimedOut(long ttlMillis);

  boolean isTimedOutUnsent();
