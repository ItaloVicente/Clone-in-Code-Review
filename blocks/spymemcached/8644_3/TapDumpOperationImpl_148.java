public class TapDumpOperationImpl extends TapOperationImpl implements
    TapOperation {
  private final String id;

  TapDumpOperationImpl(String id, OperationCallback cb) {
    super(cb);
    this.id = id;
  }

  @Override
  public void initialize() {
    RequestMessage message = new RequestMessage();
    message.setMagic(TapMagic.PROTOCOL_BINARY_REQ);
    message.setOpcode(TapOpcode.REQUEST);
    message.setFlags(TapFlag.DUMP);
    message.setFlags(TapFlag.SUPPORT_ACK);
    if (id != null) {
      message.setName(id);
    } else {
      message.setName(UUID.randomUUID().toString());
    }

    setBuffer(message.getBytes());
  }

  @Override
  public void streamClosed(OperationState state) {
    transitionState(state);
  }
