  public String connectionsStatus() {
    StringBuilder connStatus = new StringBuilder();
    connStatus.append("Connection Status {");
    for (MemcachedNode node : locator.getAll()) {
      connStatus.append(" ");
      connStatus.append(node.getSocketAddress())
        .append(" active: ").append(node.isActive())
        .append(MessageFormat.format("last read: {0} ms ago",
        node.lastReadDelta()));
    }

    connStatus.append("}");
    return connStatus.toString();
  }

