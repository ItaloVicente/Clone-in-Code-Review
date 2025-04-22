final class VersionOperationImpl extends OperationImpl implements
    VersionOperation, NoopOperation {

  private static final byte[] REQUEST = "version\r\n".getBytes();

  public VersionOperationImpl(OperationCallback c) {
    super(c);
  }

  @Override
  public void handleLine(String line) {
    assert line.startsWith("VERSION ");
    getCallback().receivedStatus(
        new OperationStatus(true, line.substring("VERSION ".length())));
    transitionState(OperationState.COMPLETE);
  }

  @Override
  public void initialize() {
    setBuffer(ByteBuffer.wrap(REQUEST));
  }
