
    List<ViewNode> nodesToRemove = new ArrayList<ViewNode>();
    for(ViewNode node : couchNodes) {
      if (node != null) {
        String hostname = node.getSocketAddress().getHostName();
        if (node.hasWriteOps()) {
          getLogger().warn("Shutting down " + hostname +
            " with ops waiting to be written");
        } else {
          getLogger().info("Node " + hostname +
            " has no ops in the queue");
