    assert o.isCancelled() || placeIn != null : "No node found for key " + key;
    if (placeIn != null) {
      if (locator instanceof VBucketNodeLocator) {
        VBucketNodeLocator vbucketLocator = (VBucketNodeLocator) locator;
        short vbucketIndex = (short) vbucketLocator.getVBucketIndex(key);
        if (o instanceof VBucketAware) {
          VBucketAware vbucketAwareOp = (VBucketAware) o;
          vbucketAwareOp.setVBucket(key, vbucketIndex);
          if (!vbucketAwareOp.getNotMyVbucketNodes().isEmpty()) {
            MemcachedNode alternative =
                vbucketLocator.getAlternative(key,
                    vbucketAwareOp.getNotMyVbucketNodes());
            if (alternative != null) {
              placeIn = alternative;
            }
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
        }
        getLogger().debug("Shut down channel %s", qa.getChannel());
      }
    }
    running = false;
    selector.close();
    getLogger().debug("Shut down selector %s", selector);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{MemcachedConnection to");
    for (MemcachedNode qa : locator.getAll()) {
      sb.append(" ");
      sb.append(qa.getSocketAddress());
    }
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
