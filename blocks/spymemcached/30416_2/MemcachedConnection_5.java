    long delay = (long) Math.min(maxDelay, Math.pow(2,
        node.getReconnectCount())) * 1000;
    long reconnectTime = System.currentTimeMillis() + delay;
    while (reconnectQueue.containsKey(reconnectTime)) {
      reconnectTime++;
    }
