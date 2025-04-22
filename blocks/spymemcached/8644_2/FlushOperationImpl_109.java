final class FlushOperationImpl extends OperationImpl implements FlushOperation {

  private static final byte[] FLUSH = "flush_all\r\n".getBytes();

  private static final OperationStatus OK = new OperationStatus(true, "OK");

  private final int delay;

  public FlushOperationImpl(int d, OperationCallback cb) {
    super(cb);
    delay = d;
  }

  @Override
  public void handleLine(String line) {
    getLogger().debug("Flush completed successfully");
    getCallback().receivedStatus(matchStatus(line, OK));
    transitionState(OperationState.COMPLETE);
  }

  @Override
  public void initialize() {
    ByteBuffer b = null;
    if (delay == -1) {
      b = ByteBuffer.wrap(FLUSH);
    } else {
      b = ByteBuffer.allocate(32);
      b.put(("flush_all " + delay + "\r\n").getBytes());
      b.flip();
    }
    setBuffer(b);
  }
