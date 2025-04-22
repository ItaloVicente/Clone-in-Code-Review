    rlock.lock();
    try {
      if (couchNodes.isEmpty()) {
        getLogger().error("No server connections. Cancelling op.");
        op.cancel();
      } else {
        boolean success = false;
        int retries = 0;
        do {
          if(retries >= MAX_ADDOP_RETRIES) {
            op.cancel();
            break;
          }
          ViewNode node = couchNodes.get(getNextNode());
          if(node.isShuttingDown() || !hasActiveVBuckets(node)) {
            continue;
          }
          if(retries > 0) {
            getLogger().debug("Retrying view operation #" + op.hashCode()
              + " on node: " + node.getSocketAddress());
          }
          success = node.writeOp(op);
          if(retries > 0 && success) {
            getLogger().debug("Successfully wrote #" + op.hashCode()
              + " on node: " + node.getSocketAddress() + " after "
              + retries + " retries.");
          }
          retries++;
        } while(!success);
