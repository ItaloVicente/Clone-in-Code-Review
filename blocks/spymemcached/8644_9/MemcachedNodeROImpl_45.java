  void copyInputQueue();

  Collection<Operation> destroyInputQueue();

  void setupResend();

  void fillWriteBuffer(boolean optimizeGets);

  void transitionWriteItem();

  Operation getCurrentReadOp();

  Operation removeCurrentReadOp();

  Operation getCurrentWriteOp();

  Operation removeCurrentWriteOp();

  boolean hasReadOp();

  boolean hasWriteOp();

  void addOp(Operation op);

  void insertOp(Operation o);

  int getSelectionOps();

  ByteBuffer getRbuf();

  ByteBuffer getWbuf();

  SocketAddress getSocketAddress();

  boolean isActive();

  void reconnecting();

  void connected();

  int getReconnectCount();

  void registerChannel(SocketChannel ch, SelectionKey selectionKey);

  void setChannel(SocketChannel to);

  SocketChannel getChannel();

  void setSk(SelectionKey to);

  SelectionKey getSk();

  int getBytesRemainingToWrite();

  int writeSome() throws IOException;

  void fixupOps();

  void authComplete();

  void setupForAuth();

  void setContinuousTimeout(boolean timedOut);

  int getContinuousTimeout();
}
