  private static final long serialVersionUID = 139074906690883031L;

  private final Collection<Operation> operations;

  public CheckedOperationTimeoutException(String message, Operation op) {
    this(message, Collections.singleton(op));
  }

  public CheckedOperationTimeoutException(String message,
      Collection<Operation> ops) {
    super(createMessage(message, ops));
    operations = ops;
  }

  private static String
  createMessage(String message, Collection<Operation> ops) {
    StringBuilder rv = new StringBuilder(message);
    rv.append(" - failing node");
    rv.append(ops.size() == 1 ? ": " : "s: ");
    boolean first = true;
    for (Operation op : ops) {
      if (first) {
        first = false;
      } else {
        rv.append(", ");
      }
      MemcachedNode node = op == null ? null : op.getHandlingNode();
      rv.append(node == null ? "<unknown>" : node.getSocketAddress());
    }
    return rv.toString();
  }

  public Collection<Operation> getOperations() {
    return operations;
  }
