          }
        }
      }
      addOperation(placeIn, o);
    } else {
      assert o.isCancelled() : "No node found for " + key
          + " (and not immediately cancelled)";
    }
  }

  public void insertOperation(final MemcachedNode node, final Operation o) {
    o.setHandlingNode(node);
    o.initialize();
    node.insertOp(o);
    addedQueue.offer(node);
    Selector s = selector.wakeup();
    assert s == selector : "Wakeup returned the wrong selector.";
    getLogger().debug("Added %s to %s", o, node);
  }

  private void addOperation(final MemcachedNode node, final Operation o) {
    o.setHandlingNode(node);
    o.initialize();
    node.addOp(o);
    addedQueue.offer(node);
    Selector s = selector.wakeup();
    assert s == selector : "Wakeup returned the wrong selector.";
    getLogger().debug("Added %s to %s", o, node);
  }

  public void addOperations(final Map<MemcachedNode, Operation> ops) {

    for (Map.Entry<MemcachedNode, Operation> me : ops.entrySet()) {
      final MemcachedNode node = me.getKey();
      Operation o = me.getValue();
      if (locator instanceof VBucketNodeLocator) {
        if (o instanceof KeyedOperation && o instanceof VBucketAware) {
          Collection<String> keys = ((KeyedOperation) o).getKeys();
          VBucketNodeLocator vbucketLocator = (VBucketNodeLocator) locator;
          for (String key : keys) {
            short vbucketIndex = (short) vbucketLocator.getVBucketIndex(key);
            VBucketAware vbucketAwareOp = (VBucketAware) o;
            vbucketAwareOp.setVBucket(key, vbucketIndex);
          }
        }
      }
      o.setHandlingNode(node);
      o.initialize();
      node.addOp(o);
      addedQueue.offer(node);
    }
    Selector s = selector.wakeup();
    assert s == selector : "Wakeup returned the wrong selector.";
  }

  public CountDownLatch broadcastOperation(BroadcastOpFactory of) {
    return broadcastOperation(of, locator.getAll());
  }

  public CountDownLatch broadcastOperation(final BroadcastOpFactory of,
      Collection<MemcachedNode> nodes) {
    final CountDownLatch latch = new CountDownLatch(locator.getAll().size());
    for (MemcachedNode node : nodes) {
      Operation op = of.newOp(node, latch);
      op.initialize();
      node.addOp(op);
      op.setHandlingNode(node);
      addedQueue.offer(node);
    }
    Selector s = selector.wakeup();
    assert s == selector : "Wakeup returned the wrong selector.";
    return latch;
  }

  public void shutdown() throws IOException {
    shutDown = true;
    Selector s = selector.wakeup();
    assert s == selector : "Wakeup returned the wrong selector.";
    for (MemcachedNode qa : locator.getAll()) {
      if (qa.getChannel() != null) {
        qa.getChannel().close();
        qa.setSk(null);
        if (qa.getBytesRemainingToWrite() > 0) {
          getLogger().warn("Shut down with %d bytes remaining to write",
              qa.getBytesRemainingToWrite());
