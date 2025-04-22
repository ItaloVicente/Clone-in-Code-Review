
    Selector s = selector.wakeup();
    assert s == selector : "Wakeup returned the wrong selector.";
    for (MemcachedNode node : locator.getAll()) {
      if (node.getChannel() != null) {
        node.getChannel().close();
        node.setSk(null);
        if (node.getBytesRemainingToWrite() > 0) {
          getLogger().warn("Shut down with %d bytes remaining to write",
