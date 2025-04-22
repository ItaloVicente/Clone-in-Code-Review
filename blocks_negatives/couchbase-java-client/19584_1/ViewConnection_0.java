    couchNodes.get(getNextNode()).addOp(op);
  }

  public void handleIO() {
    for (ViewNode node : couchNodes) {
      node.doWrites();
    }

    for (ViewNode qa : nodesToShutdown) {
      nodesToShutdown.remove(qa);
      Collection<HttpOperation> notCompletedOperations = qa.destroyWriteQueue();
      try {
        qa.shutdown();
      } catch (IOException e) {
        getLogger().error("Error shutting down connection to "
            + qa.getSocketAddress());
      }
      redistributeOperations(notCompletedOperations);
    }
  }

  private void redistributeOperations(Collection<HttpOperation> ops) {
    int added = 0;
    for (HttpOperation op : ops) {
      addOp(op);
      added++;
