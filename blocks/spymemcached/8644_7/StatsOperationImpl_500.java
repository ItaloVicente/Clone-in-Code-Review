  implements StatsOperation {

  private static final int CMD = 0x10;
  private final String key;

  public StatsOperationImpl(String arg, StatsOperation.Callback c) {
    super(CMD, generateOpaque(), c);
    key = (arg == null) ? "" : arg;
  }

  @Override
  public void initialize() {
    prepareBuffer(key, 0, EMPTY_BYTES);
  }
