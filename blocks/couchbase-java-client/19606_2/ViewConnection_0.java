    rlock.lock();
    try {
      if (couchNodes.isEmpty()) {
        getLogger().info("No server connections. Cancelling op.");
        op.cancel();
      } else {
        ViewNode node = couchNodes.get(getNextNode());
        node.writeOp(op);
