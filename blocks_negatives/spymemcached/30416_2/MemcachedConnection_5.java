      final MemcachedNode node = me.getKey();
      Operation o = me.getValue();
      o.setHandlingNode(node);
      o.initialize();
      node.addOp(o);
      addedQueue.offer(node);
      metrics.markMeter(OVERALL_REQUEST_METRIC);
