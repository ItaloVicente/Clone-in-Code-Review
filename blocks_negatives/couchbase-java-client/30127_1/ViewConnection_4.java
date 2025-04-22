  public boolean shutdown() throws IOException {
    if (shutDown) {
      getLogger().info("Suppressing duplicate attempt to shut down");
      return false;
    }

    shutDown = true;
    running = false;

    List<ViewNode> nodesToRemove = new ArrayList<ViewNode>();
    for(ViewNode node : couchNodes) {
      if (node != null) {
        String hostname = node.getSocketAddress().getHostName();
        if (node.hasWriteOps()) {
          getLogger().warn("Shutting down " + hostname
            + " with ops waiting to be written");
        } else {
          getLogger().info("Node " + hostname
            + " has no ops in the queue");
        }
        node.shutdown();
        nodesToRemove.add(node);
      }
    }

    for(ViewNode node : nodesToRemove) {
      couchNodes.remove(node);
    }

    return true;
  }
