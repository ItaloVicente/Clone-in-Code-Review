    sb.append("}");
    return sb.toString();
  }

  public static void opTimedOut(Operation op) {
    MemcachedConnection.setTimeout(op, true);
  }

  public static void opSucceeded(Operation op) {
    MemcachedConnection.setTimeout(op, false);
  }

  private static void setTimeout(Operation op, boolean isTimeout) {
    try {
      if (op == null || op.isTimedOutUnsent()) {
        return; // op may be null in some cases, e.g. flush
      }
      MemcachedNode node = op.getHandlingNode();
      if (node == null) {
        LoggerFactory.getLogger(MemcachedConnection.class).warn(
            "handling node for operation is not set");
      } else {
        node.setContinuousTimeout(isTimeout);
      }
    } catch (Exception e) {
      LoggerFactory.getLogger(MemcachedConnection.class).error(e.getMessage());
    }
  }
